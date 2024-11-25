import Controller.*;
import Service.TicketPoolService;
import SystemParameters.ConfigParameters;

import java.io.IOException;
import java.util.Scanner;

import static Controller.ParameterController.displaySystemParameters;

public class CliMain {

    public final static Scanner scanner = new Scanner(System.in);
    public static ConfigParameters configParameters = new ConfigParameters();
    public static MenuController menuController = new MenuController();

    public static void main(String[] args) throws Exception {

        boolean exit = false;

        // Main loop to display the welcome menu and sub-menu
        while (!exit) {
            displayWelcomeTable();
            int roleChoice = menuController.getNumberInRange(1,4);

            switch (roleChoice) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleVendorLogin();
                    break;
                case 3:
                    handleCustomerLogin();
                    break;
                case 4:
                    System.out.println("Exiting the system. Thank you!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void displayWelcomeTable() {
        System.out.println("\n==============================================");
        System.out.println("       Welcome to the Ticketing System");
        System.out.println("==============================================");
        System.out.println("       Please select your role:");
        System.out.println("        1. Admin");
        System.out.println("        2. Vendor");
        System.out.println("        3. Customer");
        System.out.println("        4. Exit");
        System.out.println("==============================================");
    }

    private static void handleAdminLogin() {
        AdminController adminController = new AdminController();
        if (adminController.adminVerification()) {
            VendorController vendorController = new VendorController();
            CustomerController customerController = new CustomerController();
            adminMenu(vendorController, customerController);

        } else {
            System.out.println("Invalid login. Returning to main menu.");
        }
    }

    private static void handleVendorLogin() throws Exception {
        String response = menuController.getYesOrNo("Are you a registered vendor? (y/n): ");

        VendorController vendorController = new VendorController();
        TicketPoolController ticketPoolController = new TicketPoolController();
        if (response.equals("y")) {
            if (vendorLogin(vendorController)) {
                System.out.println("Login successful.");
                // After successful login, proceed directly to vendor menu
                vendorMenu(vendorController, ticketPoolController); // Pass the vendorController object
            } else {
            System.out.println("Invalid login. Returning to main menu.");
            }
        }
        else if(response.equals("n")){
            vendorController.createVendorFromInput();
        }
        else {
            System.out.println("Invalid response. Returning to main menu.");
        }
    }

    private static void handleCustomerLogin() {

        String response = menuController.getYesOrNo("Are you a registered customer? (y/n): ");
        CustomerController customerController = new CustomerController();
        if (response.equals("y")) {
            if (customerLogin(customerController)) {
                customerMenu();
            } else {
                System.out.println("Invalid login. Returning to main menu.");
            }
        } else if (response.equals("no")) {

        } else {
            System.out.println("Invalid response. Returning to main menu.");
        }
    }

    private static boolean vendorLogin(VendorController vendorController) {
        System.out.print("Enter Vendor Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Vendor Password: ");
        String password = scanner.nextLine();

        // Verify vendor and store username in VendorController
        if (vendorController.vendorVerification(username, password)) {
            // Store username in the controller for further use
            vendorController.setUsername(username);
            vendorController.getVendorByEmail(username);
            return true;
        }
        return false;
    }

    private static boolean customerLogin(CustomerController customerController) {
        System.out.print("Enter Customer Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Customer Password: ");
        String password = scanner.nextLine();

        // Verify vendor and store username in VendorController
        if (customerController.customerVerification(username, password)) {
            // Store username in the controller for further use
            customerController.setUsername(username);
//            customerController.ge(username);
            return true;
        }
        return false;
    }

    private static void adminMenu(VendorController vendorController, CustomerController customerController) {
        boolean exitAdminMenu = false;

        while (!exitAdminMenu) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Set System Parameters");
            System.out.println("2. Manage Vendors");
            System.out.println("3. Manage Customers");
            System.out.println("4. Return to Main Menu");

            int choice = menuController.getNumberInRange(1,4);

            switch (choice) {
                case 1:
                    setSystemParameters();
                    break;
                case 2:
                    System.out.println("Managing vendors...");
                    vendorController.getAllVendors();
                    if(MenuController.adminDeleteManagement("customer")){
                        System.out.print("Enter the ID of the customer that needs to be deleted: ");
                        String deleteId = scanner.nextLine();
                        vendorController.deleteVendor(Integer.parseInt(deleteId));
                    };
                    break;
                case 3:
                    System.out.println("Managing customers...");
                    customerController.getAllCustomers();
                    if(MenuController.adminDeleteManagement("customer")){
                        System.out.print("Enter the ID of the customer that needs to be deleted: ");
                        String deleteId = scanner.nextLine();
                        customerController.deleteCustomer(Integer.parseInt(deleteId));
                    };
                    break;
                case 4:
                    exitAdminMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void vendorMenu(VendorController vendorController, TicketPoolController ticketPoolController) throws Exception {
        boolean exitVendorMenu = false;
        EventController eventController = new EventController();

        while (!exitVendorMenu) {
            String header = "--- " + vendorController.getUsername() + " Vendor Menu ---";
            int tableWidth = Math.max(header.length(), 38); // Ensures the table width accommodates the header

            // Print the vendor menu in a table format
            System.out.println("\n" + header);

            // Print top border of the table
            System.out.println("+".repeat(tableWidth));

            // Print the options
            System.out.println("| Option" + " ".repeat(tableWidth - 9 - 1) + "|");
            System.out.println("| 1. View Existing" + " ".repeat(tableWidth - 16 - 1) + "|");
            System.out.println("| 2. Create Event" + " ".repeat(tableWidth - 16 - 1) + "|");
            System.out.println("| 3. Start Event" + " ".repeat(tableWidth - 16 - 1) + "|");
            System.out.println("| 4. Delete Event" + " ".repeat(tableWidth - 16 - 1) + "|");
            System.out.println("| 5. Return to Main" + " ".repeat(tableWidth - 18 - 1) + "|");

            // Print bottom border of the table
            System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

            // Prompt for user input
            int choice = menuController.getNumberInRange(1,5);

            switch (choice) {
                case 1:
                    eventController.getEventsByVendorId(Integer.parseInt(vendorController.getId()));
                    break;
                case 2:
                    System.out.println("Creating a new event...");
                    eventController.createEventFromInput(vendorController.getId());
                    break;
                case 3:
                    eventController.getEventsByVendorId(Integer.parseInt(vendorController.getId()));
                    String startId = menuController.getValidInput("Enter the event Id that needs to be started: ", "number");
                    eventController.activateEvent(Integer.parseInt(startId));
                    break;
                case 4:
                    eventController.getEventsByVendorId(Integer.parseInt(vendorController.getId()));
                    String deleteEventId = menuController.getValidInput("Enter the Event Id that need to be deleted: ", "number");
                    try {
                        int eventTotalTickets = eventController.getTotalTicketsOfEvent(Integer.parseInt(deleteEventId));
                        int currentTotalTickets = Integer.parseInt(configParameters.getTotalTickets());
                        int updatedTotalTickets = currentTotalTickets - eventTotalTickets;
                        configParameters.updateProperty("totalTickets", String.valueOf(updatedTotalTickets));
                        ticketPoolController.deleteTicketPoolByEventId(Integer.parseInt(deleteEventId));
                        break;
                    } catch (Exception e) {
                        break;
                    }
                case 5:
                    exitVendorMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void setSystemParameters() {
        try {
            displaySystemParameters(configParameters);
            String response = menuController.getYesOrNo("Do you want to update any property? (y/n): ");

            while (response.equals("y")) {
                int key = menuController.getNumberInRange(1, 3);
                String value = menuController.getValidInput("Enter the value: ", "number");
                String systemParameter;
                if(key == 1){
                    if(Integer.parseInt(configParameters.getTotalTickets()) > Integer.parseInt(value) ){
                        throw new Exception("Max Ticket Capacity can't be lesser than Total Tickets !");
                    }
                    systemParameter = "maxTicketCapacity";
                } else if(key == 2) {
                    systemParameter = "ticketReleaseRate";
                }
                else{
                    systemParameter = "customerRetrievalRate";
                }

                configParameters.updateProperty(systemParameter, value);
                response = menuController.getYesOrNo("Do you want to update any property? (y/n): ");
                displaySystemParameters(configParameters);
            }

            System.out.println("Exiting configuration update process.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void customerMenu() {
        boolean exitCustomerMenu = false;

        while (!exitCustomerMenu) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. View Booked Tickets");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = menuController.getNumberInRange(1,3);

            switch (choice) {
                case 1:
                    System.out.println("Displaying available events...");
                    break;
                case 2:
                    System.out.println("Booking a ticket...");
                    break;
                case 3:
                    exitCustomerMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}