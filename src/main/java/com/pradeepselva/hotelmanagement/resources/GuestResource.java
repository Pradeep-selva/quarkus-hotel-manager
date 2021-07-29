package com.pradeepselva.hotelmanagement.resources;

import com.pradeepselva.hotelmanagement.entity.Guest;
import com.pradeepselva.hotelmanagement.services.GuestService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/guests")
public class GuestResource {
    @Inject
    GuestService guestService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGuests(
            @DefaultValue("")
            @QueryParam("email") String email,
            @DefaultValue("")
            @QueryParam("phone") String phone
            ) {
        if(email.equals("") && phone.equals("")) {
            return guestService.getGuests();
        }

        return guestService.getGuestByEmailOrPhone(email, phone);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGuestById(@PathParam("id") Long id) {
        return guestService.getGuestById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewGuest(Guest guest) {
        return guestService.persistGuest(guest);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGuest(Guest guest) {
        return guestService.updateGuest(guest);
    }
}
