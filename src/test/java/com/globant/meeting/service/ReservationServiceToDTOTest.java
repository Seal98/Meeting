package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.*;
import com.globant.meeting.entity.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
@Slf4j
public class ReservationServiceToDTOTest {

    @Autowired
    ReservationService reservationService;

    Building building;
    BuildingDTO buildingDTO;

    Floor floor;
    FloorDTO floorDTO;

    Room room;
    RoomDTO roomDTO;

    Reservation reservation;
    ReservationDTO reservationDTO;

    ReservationType reservationType;
    ReservationStatus reservationStatus;

    static final String BUILDING_ADDRESS_TEST = "Building Sample Address";
    static final String BUILDING_NAME_TEST = "Building Sample Name";
    static final String RESERVATION_TYPE_NAME_TEST = "RESERVATION TYPE SAMPLE NAME";
    static final String RESERVATION_TYPE_LABEL_TEST = "RESERVATION TYPE SAMPLE LABEL";
    static final String RESERVATION_STATUS_NAME_TEST = "RESERVATION STATUS SAMPLE NAME";
    static final String RESERVATION_STATUS_LABEL_TEST = "RESERVATION STATUS SAMPLE LABEL";

    @BeforeEach
    void setUp() {
        building = Building.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();
        buildingDTO = BuildingDTO.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();

        floor = Floor.builder().id(7).building(building).floorNumber(13).build();
        floorDTO = FloorDTO.builder().id(7).building(buildingDTO).floorNumber(13).build();

        room = Room.builder().id(9).number(256).seatsNumber(8).isMultimediaAvailable(true).floor(floor).build();
        roomDTO = RoomDTO.builder().id(9).number(256).seatsNumber(8).isMultimediaAvailable(true).floor(floorDTO).build();

        reservationType = ReservationType.builder().id(15).name(RESERVATION_TYPE_NAME_TEST)
                .label(RESERVATION_TYPE_LABEL_TEST).build();
        reservationStatus = ReservationStatus.builder().id(16).name(RESERVATION_STATUS_NAME_TEST)
                .label(RESERVATION_STATUS_LABEL_TEST).build();

        reservation = Reservation.builder().id(11).room(room).startTs(new Timestamp(12312312))
                .endTs(new Timestamp(23121231)).reservationType(reservationType).reservationStatus(reservationStatus)
                .build();
        reservationDTO = ReservationDTO.builder().id(11).room(roomDTO).startTs(new Timestamp(12312312))
                .endTs(new Timestamp(23121231)).reservationType(reservationType).reservationStatus(reservationStatus)
                .build();
    }

    @Test
    void checkToDto() {
        assert(reservationService.toDTO(reservation)).equals(reservationDTO);
    }

}
