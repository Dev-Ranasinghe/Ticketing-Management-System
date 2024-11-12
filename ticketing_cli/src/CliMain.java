import Controller.AdminController;
import Controller.CustomerController;
import Controller.EventController;
import Controller.VendorController;
import SystemParameters.ConfigParameters;

import java.io.IOException;
import java.util.Scanner;

import static Controller.ParameterController.displaySystemParameters;

public class CliMain {

    public final static Scanner scanner = new Scanner(System.in);
    public static ConfigParameters configParameters = new ConfigParameters();
    public static AdminController adminController = new AdminController();
    public static VendorController vendorController = new VendorController();
    public static EventController eventController = new EventController();
    public static CustomerController customerController = new CustomerController();

    public static void main(String[] args) throws IOException, InterruptedException {

        boolean exit = false;

        // Main loop to display the welcome menu and sub-menu
        while (!exit) {
            displayWelcomeTable();
            System.out.print("Enter your choice (1-3 for roles, 4 to exit): ");
            int roleChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

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
        if (adminController.adminVerification()) {
            adminMenu();
        } else {
            System.out.println("Invalid login. Returning to main menu.");
        }
    }

    private static void handleVendorLogin() {
        System.out.print("Are you a registered vendor? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            if (vendorLogin()) {
                vendorMenu();
            } else {
                System.out.println("Invalid login. Returning to main menu.");
            }
        } else if (response.equals("no")) {
            vendorController.createVendorFromInput();
            vendorMenu();
        } else {
            System.out.println("Invalid response. Returning to main menu.");
        }
    }

    private static void handleCustomerLogin() {
        System.out.print("Are you a registered customer? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("yes")) {
            if (customerLogin()) {
                customerMenu();
            } else {
                System.out.println("Invalid login. Returning to main menu.");
            }
        } else if (response.equals("no")) {

        } else {
            System.out.println("Invalid response. Returning to main menu.");
        }
    }

    private static boolean adminLogin() {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();

        return "123".equals(username) && "123".equals(password);
    }

    private static boolean vendorLogin() {
        System.out.print("Enter Vendor Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Vendor Password: ");
        String password = scanner.nextLine();

        return vendorController.vendorVerification(username, password);
    }

    private static boolean customerLogin() {
        System.out.print("Enter Customer Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Customer Password: ");
        String password = scanner.nextLine();

        return "customer".equals(username) && "cust@123".equals(password);
    }

    private static void createVendorAccount() {
        System.out.println("Registering new vendor...");

        System.out.print("Enter Vendor Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Vendor Contact (e.g., phone number): ");
        String contact = scanner.nextLine();

        System.out.print("Enter Vendor Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Vendor Password: ");
        String password = scanner.nextLine();

        System.out.println("Vendor account created successfully.");
    }

    private static void adminMenu() {
        boolean exitAdminMenu = false;

        while (!exitAdminMenu) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Set System Parameters");
            System.out.println("2. Manage Vendors");
            System.out.println("4. Manage Customers");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    setSystemParameters();
                    break;
                case 2:
                    System.out.println("Managing vendors...");
                    vendorController.getAllVendors();
                    System.out.println("sample text");
                    break;
                case 3:
                    System.out.println("Managing events...");
                    eventController.getAllEvents();
                    break;
                case 4:
                    System.out.println("Managing customers...");
                    customerController.getAllCustomers();
                    break;
                case 5:
                    exitAdminMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void vendorMenu() {
        boolean exitVendorMenu = false;

        while (!exitVendorMenu) {
            System.out.println("\n--- Vendor Menu ---");
            System.out.println("1. View Existing Events");
            System.out.println("2. Create Event");
            System.out.println("3. Edit Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Viewing vendor details...");
                    break;
                case 2:
                    System.out.println("Creating a new event...");
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

// main => controller => runnable