package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.*;
import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.exception.ReservationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;


@SpringBootTest
@Slf4j
public class ReservationServiceImplTest {

    @Autowired
    ReservationService reservationService;

    ReservationDTO reservation;

    @BeforeEach
    void setUp() {
        try {
            reservation = reservationService.reserveMeeting(
                    new Timestamp(1899936000000L),
                    new Timestamp(1899937800000L),
                    5, 2, true);
        } catch (ReservationException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("8.0.0 Check if meeting can be reserved correctly and the return value is valid")
    void checkMeetingReservation() {
        assert reservation != null && reservationService.getReservationById(reservation.getId()).equals(reservation);
    }

}
