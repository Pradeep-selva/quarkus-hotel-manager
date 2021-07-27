package com.pradeepselva.hotelmanagement.resources;

import com.pradeepselva.hotelmanagement.entity.Room;
import com.pradeepselva.hotelmanagement.services.RoomService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/rooms")
public class RoomResource {
    @Inject
    RoomService roomService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllRooms() {
        return roomService.getRooms();
    }

    @GET
    @Path("/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomByNumber(@PathParam("number") String number) {
        return roomService.getRoomByNumber(number);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewRoom(Room room) {
        return roomService.newRoom(room);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoom(Room room) {
        return roomService.updateRoom(room);
    }
}