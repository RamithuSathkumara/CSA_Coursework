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
import com.example.coursework.exception.LinkedResourceNotFoundException;
import com.example.coursework.model.Room;
import com.example.coursework.model.Sensor;

@Path("/sensors")
public class SensorResource {

    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(DummyDatabase.SENSORS);
    private GenericDAO<Room> roomDAO = new GenericDAO<>(DummyDatabase.ROOMS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addSensor(Sensor sensor) {
        Room room = roomDAO.getById(sensor.getRoomId());
        
        // Check whether the room exist.
        if (room == null) {
            throw new LinkedResourceNotFoundException("Cannot add the sensor. " + "Room with ID " + sensor.getRoomId() + " does not exist.");
        }
        
        // Add the sensor if a room exist.
        sensorDAO.add(sensor);

        room.getSensorIds().add(sensor.getId());

        // Update the room in the database to save the change
        roomDAO.update(room);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {
        List<Sensor> allSensors = sensorDAO.getAll();
        if (type == null || type.trim().isEmpty()) {
            return allSensors;
        }
        List<Sensor> filteredSensors = new ArrayList<>();
        for (Sensor s : allSensors) {
            if (s.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(s);
            }
        }
        return filteredSensors;
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String id) {
        return new SensorReadingResource(id);
    }

}
