package com.pradeepselva.quarkus.crud.services;

import com.pradeepselva.quarkus.crud.entity.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
public class RoomService {
    public Iterable<Room> getRooms() {
        return Room.listAll();
    }

    @Transactional
    public Response newRoom(Room room) {
        if(room.getRoomName() == null || room.getRoomNumber() == null || room.getBedInfo() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Room.persist(room);

        if(room.isPersistent()) {
            return Response.created(URI.create("/room"+room.getRoomId())).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}