package com.ticketing_system.service;

import com.ticketing_system.entity.TicketPool;

import java.util.List;

public interface TicketPoolService {
    public List<TicketPool> getAllTicketPools();
    public TicketPool getTicketPoolById(Integer id);
    public TicketPool saveTicketPool(TicketPool ticketPool);
    public void deleteTicketPool(Integer id);
    void deleteTicketPoolByEventId(Integer eventId);
}
