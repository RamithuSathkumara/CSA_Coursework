/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.coursework.resources;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ramithusathkumara
 */
@Path("/")
public class DiscoveryResource {
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDiscoveryInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("apiVersion", "1");
        info.put("adminContact", "ramithu.20231312@iit.ac.lk");

        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");

        info.put("links", links);

        return info;
    }
}