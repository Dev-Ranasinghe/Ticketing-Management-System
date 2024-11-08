package com.ticketing_system.cli;

import com.ticketing_system.controller.CustomerController;
import com.ticketing_system.controller.EventController;
import com.ticketing_system.controller.PurchaseController;
import com.ticketing_system.controller.VendorController;

public class ControllerFactory {

    private final CustomerController customerController;
    private final VendorController vendorController;
    private final EventController eventController;
    private final PurchaseController purchaseController;
    private final static ControllerFactory controllerFactory = new ControllerFactory();

    private ControllerFactory(){
        customerController = new CustomerController();
        vendorController = new VendorController();
        eventController = new EventController();
        purchaseController = new PurchaseController();
    }

    public CustomerController getCustomerController(){
        return customerController;
    }

    public VendorController getVendorController(){
        return vendorController;
    }

    public EventController getEventController(){
        return eventController;
    }

    public PurchaseController getPurchaseController(){
        return purchaseController;
    }

    public static ControllerFactory getInstance(){
        return controllerFactory;
    }
}
