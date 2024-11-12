package com.ticketing_system.service;

import com.ticketing_system.entity.Event;
import com.ticketing_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class EventServiceImpl implements EventService{

    private final Lock lock = new ReentrantLock();

    @Autowired
    private EventRepository eventRepository;

    // Get all customers
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get a single event
    public Event getEventById(Integer id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<Event> task = () -> {
            return eventRepository.findById(id).orElse(null);
        };
        Future<Event> future = executor.submit(task);
        Event event = null;
        try {
            event = future.get();  // This will block and wait for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return event;
    }

    // Create or update a event
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // Delete an event by ID
    public void deleteEvent(Integer id) {
        Thread thread = null;
        try {
            thread = new Thread(() -> {
                 lock.lock();
                 try {
                     eventRepository.deleteById(id);
                 } finally {
                     lock.unlock();
                 }
             });
            thread.start();
        } finally{
            thread.interrupt();
        }
    }

    // Get events by vendor ID
    public List<Event> getEventsByVendorId(Integer vendorId) {
        return eventRepository.findByVendor_VendorId(vendorId);
    }
}