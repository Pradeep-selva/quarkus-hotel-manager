package com.pradeepselva.hotelmanagement.services;

import com.pradeepselva.hotelmanagement.entity.Guest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GuestService {
    public Response getGuests() {
        Iterable<Guest> allGuests = Guest.listAll();
        return Response.ok(allGuests).build();
    }

    public Response getGuestByEmailOrPhone(String email, String phoneNo) {
       PanacheQuery<Guest> guests = Guest.find("email = ?1 or phone_number = ?2", email, phoneNo);
       List<Guest> _guests = guests.stream().collect(Collectors.toList());
       System.out.println(_guests);

        return Response.ok(_guests).build();
    }

    public Response getGuestById(Long id) {
        return Guest.find("guest_id", id)
                .singleResultOptional()
                .map(guest -> Response.ok(guest).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @Transactional
    public Response persistGuest(Guest guest) {
        if(guest.getFirstName() == null ||guest.getLastName() == null || guest.getEmail() == null || guest.getPhoneNo() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Guest.persist(guest);

        if(guest.isPersistent()) {
            return Response.created(URI.create("/guest"+guest.getGuestId())).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Transactional
    public Response updateGuest(Guest guest){
        long guestCount = Guest.find("guest_id", guest.getGuestId()).count();

        if(guestCount == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(guest.getEmail() == null || guest.getPhoneNo() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            Guest.update("email_address = ?1, address = ?2, country = ?3, state = ?4, phone_number = ?5",
                    guest.getEmail(), guest.getAddress(), guest.getCountry(), guest.getState(), guest.getPhoneNo());

            return Response.ok(guest).build();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Transactional
    public Response deleteGuest(Long id) {
        long guestCount = Guest.find("guest_id", id).count();

        if(guestCount == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            Guest.delete("guest_id", id);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
