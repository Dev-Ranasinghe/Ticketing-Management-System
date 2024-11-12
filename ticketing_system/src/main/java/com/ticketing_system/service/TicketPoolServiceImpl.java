package com.ticketing_system.service;

import com.ticketing_system.entity.TicketPool;
import com.ticketing_system.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPoolServiceImpl implements TicketPoolService{

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    public List<TicketPool> getAllTicketPools() {
        return ticketPoolRepository.findAll();
    }

    @Override
    public TicketPool getTicketPoolById(Integer id) {
        return ticketPoolRepository.findById(id).orElse(null);
    }

    @Override
    public TicketPool saveTicketPool(TicketPool ticketPool) {
        return ticketPoolRepository.save(ticketPool);
    }

    @Override
    public void deleteTicketPool(Integer id) {
        ticketPoolRepository.deleteById(id);
    }
}