package com.globant.meeting.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RoomDTOTest {

    RoomDTO roomDTO;

    FloorDTO floorDTO = new FloorDTO();

    @BeforeEach
    void setUp() {
        roomDTO = RoomDTO.builder()
                .id(3)
                .floor(floorDTO)
                .number(7)
                .seatsNumber(13)
                .isMultimediaAvailable(true)
                .build();
    }

    @Test
    @DisplayName("5.0.0 Check if Room DTO can be created correctly")
    void checkRoomDTOCreationWithAllParams(){
        assert(roomDTO.getId() == 3);
        assert(roomDTO.getFloor() == floorDTO);
        assert(roomDTO.getNumber() == 7);
        assert(roomDTO.getSeatsNumber() == 13);
        assert(roomDTO.isMultimediaAvailable());
    }

    @Test
    @DisplayName("5.0.1 Check if Building DTO No Args constructor works as expected")
    void checkRoomDTOCreationWithNoParams(){
        roomDTO = new RoomDTO();
        assert(roomDTO.getId() == 0);
        assert(roomDTO.getFloor() == null);
        assert(roomDTO.getNumber() == 0);
        assert(roomDTO.getSeatsNumber() == 0);
        assert(!roomDTO.isMultimediaAvailable());
    }

}