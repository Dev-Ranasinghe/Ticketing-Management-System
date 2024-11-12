package com.ticketing_system.controller;

import com.ticketing_system.entity.Event;
import com.ticketing_system.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")

public class EventController {

    @Autowired
    private EventServiceImpl eventServiceImpl;

    @GetMapping
    public List<Event> getAllEvents(){
        return eventServiceImpl.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventServiceImpl.getEventById(id);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Event> getEventsByVendorId(@PathVariable Integer vendorId) {
        return eventServiceImpl.getEventsByVendorId(vendorId);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventServiceImpl.saveEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventServiceImpl.deleteEvent(id);
    }

}