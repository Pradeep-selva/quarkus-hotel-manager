package com.pradeepselva.hotelmanagement.resources;

import com.pradeepselva.hotelmanagement.entity.Reservation;
import com.pradeepselva.hotelmanagement.services.ReservationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/reservations")
public class ReservationResource {
    @Inject
    ReservationService reservationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(
            @QueryParam("roomId")
            Long roomId,
            @QueryParam("guestId")
            Long guestId
            ) {
        if(roomId != null || guestId != null) {
            return reservationService.getReservationsByGuestAndRoom(roomId, guestId);
        }
        return reservationService.getAllReservations();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationById(@PathParam("id") Long id) {
        return reservationService.getReservationsById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReservation(Reservation reservation) {
        return reservationService.newReservation(reservation);
    }
}
