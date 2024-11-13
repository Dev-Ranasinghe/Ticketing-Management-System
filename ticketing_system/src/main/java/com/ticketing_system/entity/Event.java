package com.ticketing_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "eventStatus", nullable = false)
    private boolean eventStatus;
    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", nullable = false)
    @JsonBackReference
    private Vendor vendor;
}

// ticket pool table (eventId, availableReleaseTicketCount)