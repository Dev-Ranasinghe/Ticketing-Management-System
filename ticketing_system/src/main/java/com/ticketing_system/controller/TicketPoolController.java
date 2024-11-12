package com.ticketing_system.controller;

import com.ticketing_system.entity.Event;
import com.ticketing_system.entity.TicketPool;
import com.ticketing_system.service.EventServiceImpl;
import com.ticketing_system.service.TicketPoolService;
import com.ticketing_system.service.TicketPoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-pool")

public class TicketPoolController {

    @Autowired
    private TicketPoolServiceImpl ticketPoolServiceImpl;

    @GetMapping
    public List<TicketPool> getAllTicketPools(){
        return ticketPoolServiceImpl.getAllTicketPools();
    }

    @GetMapping("/{id}")
    public TicketPool getTicketPoolById(@PathVariable Integer id) {
        return ticketPoolServiceImpl.getTicketPoolById(id);
    }

    @PostMapping
    public TicketPool createTicketPool(@RequestBody TicketPool ticketPool) {
        return ticketPoolServiceImpl.saveTicketPool(ticketPool);
    }

    @DeleteMapping("/{id}")
    public void deleteTicketPool(@PathVariable Integer id) {
        ticketPoolServiceImpl.deleteTicketPool(id);
    }

}