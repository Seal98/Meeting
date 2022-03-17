package com.globant.meeting.dto;

import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.entity.ReservationStatus;
import com.globant.meeting.entity.ReservationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
@Slf4j
public class ReservationDTOTest {

    ReservationDTO reservationDTO;

    RoomDTO roomDTO = new RoomDTO();

    ReservationType reservationType = new ReservationType();

    ReservationStatus reservationStatus = new ReservationStatus();

    @BeforeEach
    void setUp() {
        reservationDTO = ReservationDTO.builder()
                .id(3)
                .startTs(new Timestamp(123231231))
                .endTs(new Timestamp(334231231))
                .room(roomDTO)
                .reservationStatus(reservationStatus)
                .reservationType(reservationType)
                .build();
    }

    @Test
    @DisplayName("4.0.0 Check if Reservation DTO can be created correctly")
    void checkReservationDTOCreationWithAllParams(){
        assert(reservationDTO.getId() == 3);
        assert(reservationDTO.getStartTs().getTime() == 123231231);
        assert(reservationDTO.getEndTs().getTime() == 334231231);
        assert(reservationDTO.getReservationStatus() == reservationStatus);
        assert(reservationDTO.getReservationType() == reservationType);
    }

    @Test
    @DisplayName("4.0.1 Check if Reservation DTO No Args constructor works as expected")
    void checkReservationDTOCreationWithNoParams(){
        reservationDTO = new ReservationDTO();
        assert(reservationDTO.getId() == 0);
        assert(reservationDTO.getStartTs() == null);
        assert(reservationDTO.getEndTs() == null);
        assert(reservationDTO.getReservationStatus() == null);
        assert(reservationDTO.getReservationType() == null);
    }

}