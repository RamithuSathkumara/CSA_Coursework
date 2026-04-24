/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.coursework.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.coursework.model.Room;
import com.example.coursework.model.Sensor;
import com.example.coursework.model.SensorReading;
import java.util.Arrays;
/**
 *
 * @author ramithusathkumara
 */
public class DummyDatabase {
    public static final List<Room> ROOMS = new ArrayList<>();
    public static final List<Sensor> SENSORS = new ArrayList<>();
    public static final List<SensorReading> READINGS = new ArrayList<>();
    
    static{
        // Initialise rooms and add to the arraylist
        Room library = new Room("LIB-301", "Library", 30);
        Room cafeteria = new Room("CAF-100", "Cafeteria", 100);
        
        ROOMS.add(library);
        ROOMS.add(cafeteria);
        
        // Initialise sensors and add to the arraylist
        Sensor tempSensor = new Sensor("TEMP-101", "Temperature", "ACTIVE", 25, "LIB-301");
        Sensor co2Sensor = new Sensor("CO2-101", "CO2", "ACTIVE", 16.5, "CAF-100");
        
        SENSORS.add(tempSensor);
        SENSORS.add(co2Sensor);
        
        // Add sensors to the room's sensorId arraylist.
        library.getSensorIds().add(tempSensor.getId());
        cafeteria.getSensorIds().add(co2Sensor.getId());
        
        // Add dummy sensor readings
        READINGS.add(new SensorReading(java.util.UUID.randomUUID().toString(),System.currentTimeMillis(),27.5,"TEMP-101"));
        READINGS.add(new SensorReading(java.util.UUID.randomUUID().toString(), System.currentTimeMillis(), 70.0, "CO2-101"));
    }
}
