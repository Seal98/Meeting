package com.globant.meeting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESERVATION")
public class Reservation {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "START_TS")
    private Timestamp startTs;

    @Column(name = "END_TS")
    private Timestamp endTs;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_TYPE_ID")
    private ReservationType reservationType;

    @ManyToOne
    @JoinColumn(name = "RESERVATION_STATUS_ID")
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;
}