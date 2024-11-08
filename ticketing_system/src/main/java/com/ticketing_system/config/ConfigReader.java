package com.ticketing_system.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getTotalTickets() {
        return properties.getProperty("totalTickets");
    }

    public String getMaxTicketCapacity() {
        return properties.getProperty("maxTicketCapacity");
    }

    public String getCustomerRetrievalRate() {
        return properties.getProperty("customerRetrievalRate");
    }

    public String getTicketReleaseRate() {
        return properties.getProperty("ticketReleaseRate");
    }

    public static void main(String[] args) {
        ConfigReader configReader = new ConfigReader();
        System.out.println("Total Tickets: " + configReader.getTotalTickets());
        System.out.println("Max Ticket Capacity: " + configReader.getMaxTicketCapacity());
        System.out.println("Customer Retrieval Rate: " + configReader.getCustomerRetrievalRate());
        System.out.println("Customer Ticket Release Rate: " + configReader.getTicketReleaseRate());
    }
}
