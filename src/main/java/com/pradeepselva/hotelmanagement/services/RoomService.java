package com.pradeepselva.hotelmanagement.services;

import com.pradeepselva.hotelmanagement.entity.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
public class RoomService {
    public Response getRooms() {
        Iterable<Room> rooms =  Room.listAll();
        return Response.ok(rooms).build();
    }

    public Response getRoomByNumber(String number) {
        return Room.find("room_number", number)
                .singleResultOptional()
                .map(movie -> Response.ok(movie).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }


    @Transactional
    public Response newRoom(Room room) {
        long roomCount = Room.count("room_number", room.getRoomNumber());

        if(roomCount > 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(room.getRoomName() == null || room.getRoomNumber() == null || room.getBedInfo() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Room.persist(room);

        if(room.isPersistent()) {
            return Response.created(URI.create("/room"+room.getRoomId())).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Transactional
    public Response updateRoom(Room room) {
       long roomCount = Room.count("room_id", room.getRoomId());

       if(roomCount > 0) {
           if(room.getRoomName() == null || room.getBedInfo() == null) {
               return Response.status(Response.Status.BAD_REQUEST).build();
           }
           try {
               Room.update("name = ?1, bed_info = ?2 where room_id = ?3",
                       room.getRoomName(), room.getBedInfo(), room.getRoomId());

               return Response.ok(room).build();
           } catch (Exception e) {
               throw new IllegalStateException(e);
           }
       } else {
           return Response.status(Response.Status.NOT_FOUND).build();
       }
    }

    @Transactional
    public Response deleteRoom(Long roomId) throws IllegalAccessException {
        long roomCount = Room.count("room_id", roomId);

        if(roomCount > 0) {
            try {
                Room.delete("room_id", roomId);
                return Response.ok().build();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
