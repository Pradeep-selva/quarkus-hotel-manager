package com.pradeepselva.hotelmanagement.services;

import com.pradeepselva.hotelmanagement.entity.Guest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
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
}
