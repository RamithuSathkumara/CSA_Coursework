/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.coursework.resources;

/**
 *
 * @author ramithusathkumara
 */
import com.example.coursework.dao.GenericDAO;
import com.example.coursework.dao.DummyDatabase;
import com.example.coursework.exception.SensorUnavailableException;
import com.example.coursework.model.Sensor;
import com.example.coursework.model.SensorReading;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class SensorReadingResource {

    private GenericDAO<SensorReading> readingDAO = new GenericDAO<>(DummyDatabase.READINGS);
    private GenericDAO<Sensor> sensorDAO = new GenericDAO<>(DummyDatabase.SENSORS);

    private final String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        List<SensorReading> allReadings = readingDAO.getAll();
        List<SensorReading> filteredReadings = new ArrayList<>();
        for (SensorReading reading : allReadings) {

            if (reading.getSensorId() != null && reading.getSensorId().equals(this.sensorId)) {

                filteredReadings.add(reading);
            }
        }
        return filteredReadings;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SensorReading addReading(SensorReading reading) {
        Sensor parent = sensorDAO.getById(this.sensorId);

        if ("MAINTENANCE".equalsIgnoreCase(parent.getStatus())) {
            throw new SensorUnavailableException("Sensor " + this.sensorId + " is under maintenance and cannot accept new readings.");
        }

        reading.setSensorId(this.sensorId);
        reading.setTimestamp(System.currentTimeMillis());
        readingDAO.add(reading);
        if (parent != null) {
            parent.setCurrentValue(reading.getValue());
            sensorDAO.update(parent);
        }

        return reading;
    }
}
