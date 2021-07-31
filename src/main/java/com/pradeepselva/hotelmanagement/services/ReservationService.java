package com.pradeepselva.hotelmanagement.services;

import com.pradeepselva.hotelmanagement.entity.Guest;
import com.pradeepselva.hotelmanagement.entity.Reservation;
import com.pradeepselva.hotelmanagement.entity.Room;
import com.pradeepselva.hotelmanagement.joins.ReservationJoin;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationService {
    public ReservationJoin constructJoinResponse(List<Object[]> resList) {
        Object[] res = resList.get(0);

        return new ReservationJoin(
                ((BigInteger) res[0]).longValue(), ((BigInteger) res[1]).longValue(), ((BigInteger) res[2]).longValue(),
                (Date) res[3], (String) res[4], (String) res[5],
                res[6] + " " + res[7], (String) res[8], (String) res[9]
        );
    }

    public Response getAllReservations() {
        Iterable<Reservation> reservations = Reservation.listAll();

        return Response.ok(reservations).build();
    }

    public Response getReservationsByGuestAndRoom(Long roomId, Long guestId) {
        roomId = roomId == null?0:roomId;
        guestId = guestId == null?0:guestId;

        PanacheQuery<Reservation> query = Reservation.find("room_id = ?1 or guest_id = ?2", roomId, guestId);
        List<Reservation> reservations = query.stream().collect(Collectors.toList());

        return Response.ok(reservations).build();
    }

    public Response getReservationsById(Long id) {
        @SuppressWarnings("unchecked")
        List<Object[]> resList = (List<Object[]>) Reservation.getEntityManager().createNativeQuery(
                "select reservation.reservation_id, reservation.guest_id, " +
                        "room.room_id, reservation.res_date, room.bed_info, room.room_number, " +
                        "guest.first_name, guest.last_name, guest.email_address, guest.phone_number " +
                        "from reservation inner join room on room.room_id = reservation.room_id "+
                        "inner join guest on guest.guest_id = reservation.guest_id "+
                        "where reservation.reservation_id = "+id
        ).getResultList();

        return Response.ok(constructJoinResponse(resList)).build();
    }

    @Transactional
    public Response newReservation(Reservation reservation) {
        if(reservation.getRoomId() == null || reservation.getGuestId() == null || reservation.getResDate() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        long roomCount = Room.count("room_id", reservation.getRoomId());
        long guestCount = Guest.count("guest_id", reservation.getGuestId());

        if(roomCount == 0 || guestCount == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Reservation.persist(reservation);

        if(reservation.isPersistent()) {
            return Response.created(URI.create("/reservations"+reservation.getGuestId())).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Transactional
    public Response deleteReservation(Long reservationId) throws IllegalStateException {
        long reservationCount = Reservation.count("reservation_id", reservationId);

        if(reservationCount > 0) {
            try {
                Reservation.delete("reservation_id", reservationId);
                return Response.ok().build();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
