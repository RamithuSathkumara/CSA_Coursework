/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.coursework.resources;

/**
 *
 * @author ramithusathkumara
 */
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import com.example.coursework.dao.GenericDAO;
import com.example.coursework.dao.DummyDatabase;
import com.example.coursework.exception.RoomNotEmptyException;
import com.example.coursework.model.Room;
import javax.ws.rs.core.Response;

@Path("/rooms")
public class SensorRoomResource {

    private GenericDAO<Room> roomDAO = new GenericDAO<>(DummyDatabase.ROOMS);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return roomDAO.getAll();
    }

    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String roomId) {
        return roomDAO.getById(roomId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room) {
        roomDAO.add(room);

        return Response.status(Response.Status.CREATED)
                .entity(room)
                .build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{roomId}")
    public void deleteRoom(@PathParam("roomId") String roomId) {
        Room room = roomDAO.getById(roomId);

        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Room " + roomId + " has sensors assigned to it.");
        }

        roomDAO.delete(roomId);
    }
}
