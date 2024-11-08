package com.ticketing_system.cli;
import java.util.*;
import com.ticketing_system.controller.CustomerController;
import com.ticketing_system.controller.EventController;
import com.ticketing_system.controller.VendorController;
import org.springframework.boot.SpringApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ticketing_system")
public class MainCli implements CommandLineRunner {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private EventController eventController;
    @Autowired
    private VendorController vendorController;

    public static void main(String[] args) {

        SpringApplication.run(MainCli.class, args);  // This initializes the Spring context
    }

    @Override
    public void run(String... args) throws Exception {
        // Now you can call the method on the injected controller
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select User Type (Customer C, Vendor V, Admin A): ");
        String userType = scanner.nextLine().toUpperCase();

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if(userType.equals("C")) {
            boolean validated = customerController.customerVerification(userName, password);
            if (validated) {
                System.out.println("Validation success !");
            }
        }
    }
}