package com.ticketing_system.controller;

import com.ticketing_system.config.ConfigReader;
import com.ticketing_system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigReader configReader = new ConfigReader();

    @Autowired
    private ConfigService configService;

    @GetMapping("/total-tickets")
    public String getTotalTickets() {
        return configReader.getTotalTickets();
    }

    @GetMapping("/max-tickets")
    public String getMaxTickets() {
        return configReader.getMaxTicketCapacity();
    }

    @GetMapping("/ticket-release-rate")
    public String getTicketReleaseRate() {
        return configReader.getTicketReleaseRate();
    }

    @GetMapping("/customer-retrieval-rate")
    public String getCustomerRetrievalRate() {
        return configReader.getCustomerRetrievalRate();
    }

    @PutMapping
    public void updateProperty(@RequestParam String key, @RequestParam String value) {
        configService.updateProperty(key, value);
    }
}
