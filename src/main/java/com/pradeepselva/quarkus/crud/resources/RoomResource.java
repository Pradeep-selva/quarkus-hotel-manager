package com.pradeepselva.quarkus.crud.resources;

import com.pradeepselva.quarkus.crud.entity.Room;
import com.pradeepselva.quarkus.crud.services.RoomService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/rooms")
public class RoomResource {
    @Inject
    RoomService roomService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllRooms() {
        Iterable<Room> rooms = roomService.getRooms();
        return Response.ok(rooms).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewRoom(Room room) {
        return roomService.newRoom(room);
    }
}