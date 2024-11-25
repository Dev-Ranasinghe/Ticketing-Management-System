package com.ticketing_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data //getters and setters auto generated
@AllArgsConstructor //generate constructor with all the attributes
@NoArgsConstructor //default constructor with no args
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;
    @Column(name = "event_name", nullable = false)
    private String eventName;
    @Column(name = "event_location", nullable = false)
    private String eventLocation;
    @Column(name = "totalTickets", nullable = false)
    private Integer totalTickets;
    @Column(name = "ticket_price", nullable = false)
//    private Integer ticketPrice;
//    @Column(name = "eventStatus", nullable = false)
    private boolean eventStatus;
    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", nullable = false)
    @JsonBackReference
    private Vendor vendor;
    @OneToOne(mappedBy = "event", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private TicketPool ticketPool;

    public Event(Integer eventId, Vendor vendor, boolean eventStatus, String eventLocation, String eventName, Integer totalTickets) {
        this.eventId = eventId;
        this.vendor = vendor;
        this.eventStatus = eventStatus;
        this.eventLocation = eventLocation;
        this.eventName = eventName;
        this.totalTickets = totalTickets;
//        this.ticketPrice = ticketPrice;
    }

    ////////////////////
    public Integer getTotalTickets() {
        return totalTickets;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public boolean isEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(boolean eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

}