package Controller;

import SystemParameters.ConfigParameters;

import java.io.IOException;

public class ParameterController {
    public static void displaySystemParameters(ConfigParameters configParameters) throws IOException, InterruptedException {
        System.out.println("\n===============================================");
        System.out.println("|              System Parameters             |");
        System.out.println("===============================================");
        System.out.printf("| %-25s | %-14s |\n", "Parameter", "Value");
        System.out.println("===============================================");
        System.out.printf("| %-25s | %-14s |\n", "Total Tickets", configParameters.getTotalTickets());
        System.out.printf("| %-25s | %-14s |\n", "Max Tickets", configParameters.getMaxTickets());
        System.out.printf("| %-25s | %-14s |\n", "Ticket Release Rate", configParameters.getTicketReleaseRate());
        System.out.printf("| %-25s | %-14s |\n", "Customer Retrieval Rate", configParameters.getCustomerRetrievalRate());
        System.out.println("==============================================");
    }

}
