package Controller;

import Service.EventService;
import Service.VendorService;
import SystemParameters.ConfigParameters;

import java.io.IOException;
import java.util.Scanner;

public class EventController {

    private MenuController menuController = new MenuController();
    private ConfigParameters configParameters = new ConfigParameters();

    public void getAllEvents() {
        // Create a new thread for each service task and start it
        EventService service = new EventService("fetchAll", null, null, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getEventById(int eventId) {
        EventService service = new EventService("fetchById", eventId, null, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getEventsByVendorId(int vendorId) {
        EventService service = new EventService("fetchByVendorId", null, null, vendorId);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteEvent(int eventId) {
        EventService service = new EventService("delete", eventId, null, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createEventFromInput(String vendorId) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int availableTickets = Integer.parseInt(configParameters.getMaxTickets()) - Integer.parseInt(configParameters.getTotalTickets());
        System.out.println("Only " + availableTickets + " more tickets can be handled by the system.");
        // Gather event details from user input
        String eventName = menuController.getValidInput("Enter Event Name: ", "string");
        String eventLocation = menuController.getValidInput("Enter Event Location: ", "string");
        String totalTickets = menuController.getValidInput("Enter Total Tickets (number): ", "number");
        try {
        if(Integer.parseInt(totalTickets) >= availableTickets){
            throw new Exception("Error !!! Only " + availableTickets + " more tickets can be handled by the system.");
        }
        boolean eventStatus = false;

        // Create JSON string from user inputs
        String eventJson = String.format(
                "{\"eventName\": \"%s\", \"eventLocation\": \"%s\", \"totalTickets\": %s, \"eventStatus\": %b, \"vendor\": {\"vendorId\": %s}}",
                eventName, eventLocation, totalTickets, eventStatus, vendorId);

        // Create and start a thread to save the event
        EventService service = new EventService("save", null, eventJson, null);
        Thread thread = new Thread(service);
        thread.start();
            thread.join();  // Waits for this thread to complete before moving on
            System.out.println("Event creation request has been sent.");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
