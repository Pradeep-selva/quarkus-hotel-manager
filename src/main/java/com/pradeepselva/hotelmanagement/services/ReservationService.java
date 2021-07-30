package com.pradeepselva.hotelmanagement.services;

import com.pradeepselva.hotelmanagement.entity.Guest;
import com.pradeepselva.hotelmanagement.entity.Reservation;
import com.pradeepselva.hotelmanagement.entity.Room;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationService {
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
        return Reservation.find("reservation_id", id)
                .singleResultOptional()
                .map(reservation -> Response.ok(reservation).build())
                .orElse(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
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
}
