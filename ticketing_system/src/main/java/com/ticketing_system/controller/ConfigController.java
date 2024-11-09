package com.ticketing_system.controller;

import com.ticketing_system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/total-tickets")
    public String getTotalTickets() {
        return configService.getTotalTickets();
    }

    @GetMapping("/max-tickets")
    public String getMaxTickets() {
        return configService.getMaxTicketCapacity();
    }

    @GetMapping("/ticket-release-rate")
    public String getTicketReleaseRate() {
        return configService.getTicketReleaseRate();
    }

    @GetMapping("/customer-retrieval-rate")
    public String getCustomerRetrievalRate() {
        return configService.getCustomerRetrievalRate();
    }

    @PutMapping
    public void updateProperty(@RequestParam String key, @RequestParam String value){
        try {
            configService.updateProperty(key, value);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
