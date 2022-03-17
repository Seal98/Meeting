package com.globant.meeting.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
    void checkFloorDTOCreationWithAllParams(){
        assert(roomDTO.getId() == 3);
        assert(roomDTO.getFloor() == floorDTO);
        assert(roomDTO.getNumber() == 7);
        assert(roomDTO.getSeatsNumber() == 13);
        assert(roomDTO.isMultimediaAvailable());
    }

    @Test
    void checkFloorDTOCreationWithNoParams(){
        roomDTO = new RoomDTO();
        assert(roomDTO.getId() == 0);
        assert(roomDTO.getFloor() == null);
        assert(roomDTO.getNumber() == 0);
        assert(roomDTO.getSeatsNumber() == 0);
        assert(!roomDTO.isMultimediaAvailable());
    }

}