package Controller;

import Runnable.EventRunnable;

public class EventController {
    public void getAllEvents(){
        EventRunnable eventRunnable = new EventRunnable("fetchAll", null, null, null);
        // Run the VendorRunnable in a new thread
        Thread thread = new Thread(eventRunnable);
        thread.start();
    }
}
