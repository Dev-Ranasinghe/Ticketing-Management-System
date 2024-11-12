package Controller;

import Runnable.CustomerRunnable;

public class CustomerController {
    public void getAllCustomers(){
        CustomerRunnable customerRunnable = new CustomerRunnable("fetchAll", null, null);
        // Run the VendorRunnable in a new thread
        Thread thread = new Thread(customerRunnable);
        thread.start();
    }
}