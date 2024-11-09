import SystemParameters.*;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;

public class Main {
    private final HttpClient httpClient;
    private final ConfigUpdater configUpdater;

    public Main() {
        this.httpClient = HttpClient.newHttpClient();
        this.configUpdater = new ConfigUpdater(httpClient);
    }

    // Method to display current configurations
    public void displayCurrentConfig() throws IOException, InterruptedException {
        TotalTickets totalTickets = new TotalTickets(httpClient);
        MaxTickets maxTickets = new MaxTickets(httpClient);
        TicketReleaseRate ticketReleaseRate = new TicketReleaseRate(httpClient);
        CustomerRetrievalRate customerRetrievalRate = new CustomerRetrievalRate(httpClient);

        System.out.println("Total Tickets: " + totalTickets.getValue());
        System.out.println("Max Tickets: " + maxTickets.getValue());
        System.out.println("Ticket Release Rate: " + ticketReleaseRate.getValue());
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate.getValue());
    }

    // Method to allow user to update configurations
    public void updateConfiguration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to update any property? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();

        while (response.equals("yes")) {
            System.out.print("Enter the property key to update: ");
            String key = scanner.nextLine().trim();
            System.out.print("Enter the new value: ");
            String value = scanner.nextLine().trim();

            try {
                // Update the parameter on the server
                configUpdater.updateProperty(key, value);
                // Immediately fetch and display the updated value
                displayCurrentConfig();
            } catch (IOException | InterruptedException e) {
                System.out.println("Failed to update property: " + e.getMessage());
            }

            System.out.print("Do you want to update another property? (yes/no): ");
            response = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("Exiting configuration update process.");
        scanner.close();
    }

    public static void main(String[] args) {
        Main client = new Main();

        try {
            // Display current configurations
            client.displayCurrentConfig();
            // Allow user to update configurations
            client.updateConfiguration();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


//import SystemParameters.*;
//
//import java.io.IOException;
//import java.net.http.HttpClient;
//import java.util.Scanner;
//
//public class Main {
//    private final HttpClient httpClient;
//    private final ConfigUpdater configUpdater;
//
//    public Main() {
//        this.httpClient = HttpClient.newHttpClient();
//        this.configUpdater = new ConfigUpdater(httpClient);
//    }
//
//    public void displayCurrentConfig() throws IOException, InterruptedException {
//        TotalTickets totalTickets = new TotalTickets(httpClient);
//        MaxTickets maxTickets = new MaxTickets(httpClient);
//        TicketReleaseRate ticketReleaseRate = new TicketReleaseRate(httpClient);
//        CustomerRetrievalRate customerRetrievalRate = new CustomerRetrievalRate(httpClient);
//
//        System.out.println("Total Tickets: " + totalTickets.getValue());
//        System.out.println("Max Tickets: " + maxTickets.getValue());
//        System.out.println("Ticket Release Rate: " + ticketReleaseRate.getValue());
//        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate.getValue());
//    }
//
//    public void updateConfiguration() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Do you want to update any property? (yes/no): ");
//        String response = scanner.nextLine().trim().toLowerCase();
//
//        while (response.equals("yes")) {
//            System.out.print("Enter the property key to update: ");
//            String key = scanner.nextLine().trim();
//            System.out.print("Enter the new value: ");
//            String value = scanner.nextLine().trim();
//
//            try {
//                configUpdater.updateProperty(key, value);
//            } catch (IOException | InterruptedException e) {
//                System.out.println("Failed to update property: " + e.getMessage());
//            }
//
//            System.out.print("Do you want to update another property? (yes/no): ");
//            response = scanner.nextLine().trim().toLowerCase();
//        }
//
//        System.out.println("Exiting configuration update process.");
//        scanner.close();
//    }
//
//    public static void main(String[] args) {
//        Main client = new Main();
//
//        try {
//            // Display current configurations
//            client.displayCurrentConfig();
//            // Allow user to update configurations
//            client.updateConfiguration();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}


//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.Scanner;
//
//public class ConfigClient {
//
//    private static final String BASE_URL = "http://localhost:8080/api/config";
//    private final HttpClient httpClient;
//
//    public ConfigClient() {
//        this.httpClient = HttpClient.newHttpClient();
//    }
//
//    public String getTotalTickets() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/total-tickets"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    public String getMaxTickets() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/max-tickets"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    public String getTicketReleaseRate() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/ticket-release-rate"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    public String getCustomerRetrievalRate() throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/customer-retrieval-rate"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    public void updateProperty(String key, String value) throws IOException, InterruptedException {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL))
//                .PUT(HttpRequest.BodyPublishers.ofString("key=" + key + "&value=" + value))
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//
//        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
//
//        if (response.statusCode() == 200) {
//            System.out.println("Property updated successfully.");
//        } else {
//            System.out.println("Failed to update property: " + response.statusCode());
//        }
//    }
//
//    public static void main(String[] args) {
//        ConfigClient client = new ConfigClient();
//        Scanner scanner = new Scanner(System.in);
//
//        try {
//            // Display current configurations
//            System.out.println("Total Tickets: " + client.getTotalTickets());
//            System.out.println("Max Tickets: " + client.getMaxTickets());
//            System.out.println("Ticket Release Rate: " + client.getTicketReleaseRate());
//            System.out.println("Customer Retrieval Rate: " + client.getCustomerRetrievalRate());
//
//            // Ask user if they want to update any property
//            System.out.print("Do you want to update any property? (yes/no): ");
//            String response = scanner.nextLine().trim().toLowerCase();
//
//            while (response.equals("yes")) {
//                // Get the property key and new value from the user
//                System.out.print("Enter the property key to update: ");
//                String key = scanner.nextLine().trim();
//                System.out.print("Enter the new value: ");
//                String value = scanner.nextLine().trim();
//
//                // Update the property
//                client.updateProperty(key, value);
//
//                // Ask if they want to update another property
//                System.out.print("Do you want to update another property? (yes/no): ");
//                response = scanner.nextLine().trim().toLowerCase();
//            }
//
//            System.out.println("Exiting configuration update process.");
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            scanner.close();
//        }
//    }
//}
