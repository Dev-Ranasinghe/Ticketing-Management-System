import Controller.*;
import SystemParameters.ConfigParameters;

import java.io.IOException;
import java.util.Scanner;

import static Controller.ParameterController.displaySystemParameters;

public class CliMain {

    public final static Scanner scanner = new Scanner(System.in);
    public static ConfigParameters configParameters = new ConfigParameters();
    public static MenuController menuController;

    public static void main(String[] args) throws IOException, InterruptedException {

        boolean exit = false;

        // Main loop to display the welcome menu and sub-menu
        while (!exit) {
            displayWelcomeTable();
            int roleChoice = MenuController.getNumberInRange(1,4);

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

    private static void handleVendorLogin() {
        System.out.print("Are you a registered vendor? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        VendorController vendorController = new VendorController();
        if (response.equals("yes")) {
            if (vendorLogin(vendorController)) {
                System.out.println("Login successful.");
                // After successful login, proceed directly to vendor menu
                vendorMenu(vendorController); // Pass the vendorController object
            } else {
            System.out.println("Invalid login. Returning to main menu.");
            }
        }
        else if(response.equals("no")){
            vendorController.createVendorFromInput();
        }
        else {
            System.out.println("Invalid response. Returning to main menu.");
        }
    }

    private static void handleCustomerLogin() {

        CustomerController customerController = new CustomerController();

        System.out.print("Are you a registered customer? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
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

        return customerController.customerVerification(username, password);
    }

    private static void adminMenu(VendorController vendorController, CustomerController customerController) {
        boolean exitAdminMenu = false;

        while (!exitAdminMenu) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Set System Parameters");
            System.out.println("2. Manage Vendors");
            System.out.println("3. Manage Customers");
            System.out.println("4. Return to Main Menu");

            int choice = MenuController.getNumberInRange(1,4);

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

    private static void vendorMenu(VendorController vendorController) {
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
            System.out.println("| 3. Edit Event" + " ".repeat(tableWidth - 14 - 1) + "|");
            System.out.println("| 4. Delete Event" + " ".repeat(tableWidth - 16 - 1) + "|");
            System.out.println("| 5. Return to Main" + " ".repeat(tableWidth - 18 - 1) + "|");

            // Print bottom border of the table
            System.out.println("+" + "-".repeat(tableWidth - 2) + "+");

            // Prompt for user input
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println(vendorController.getId());
                    eventController.getEventsByVendorId(Integer.parseInt(vendorController.getId()));
                    break;
                case 2:
                    System.out.println("Creating a new event...");
                    eventController.createEventFromInput(vendorController.getId());
                    break;
                case 3:
                    System.out.println("Editing an event...");
                    break;
                case 4:
                    System.out.println("Deleting an event...");
                    break;
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
            System.out.print("Do you want to update any property? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            while (response.equals("yes")) {
                System.out.print("Enter the property key to update: ");
                String key = scanner.nextLine().trim();
                System.out.print("Enter the new value: ");
                String value = scanner.nextLine().trim();

                configParameters.updateProperty(key, value);
                System.out.print("Do you want to update another property? (yes/no): ");
                response = scanner.nextLine().trim().toLowerCase();
                displaySystemParameters(configParameters);
            }

            System.out.println("Exiting configuration update process.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void customerMenu() {
        boolean exitCustomerMenu = false;

        while (!exitCustomerMenu) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Events");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Booked Tickets");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Displaying available events...");
                    break;
                case 2:
                    System.out.println("Booking a ticket...");
                    break;
                case 3:
                    System.out.println("Viewing booked tickets...");
                    break;
                case 4:
                    System.out.println("Cancelling a ticket...");
                    break;
                case 5:
                    exitCustomerMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}