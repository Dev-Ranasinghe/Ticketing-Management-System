package com.ticketing_system.service;

import com.ticketing_system.entity.Event;
import com.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Get all customers
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get a single event
    public Event getEventById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }

    // Create or update a event
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // Delete an event by ID
    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    // Get events by vendor ID
    public List<Event> getEventsByVendorId(Integer vendorId) {
        return eventRepository.findByVendor_VendorId(vendorId);
    }
}