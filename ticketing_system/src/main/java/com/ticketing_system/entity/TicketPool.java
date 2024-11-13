package com.ticketing_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters and setters auto generated
@AllArgsConstructor //generate constructor with all the attributes
@NoArgsConstructor //default constructor with no args
@Entity
public class TicketPool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pool_id")
    private Integer poolId;
    @Column(name = "released_ticket_count", nullable = false)
    private Integer releasedTicketCount;
    @OneToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;
}