package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.Building;
import com.globant.meeting.entity.Floor;
import com.globant.meeting.entity.Room;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RoomServiceToDTOTest {

    @Autowired
    RoomService roomService;

    Building building;
    BuildingDTO buildingDTO;

    Floor floor;
    FloorDTO floorDTO;

    Room room;
    RoomDTO roomDTO;

    static final String BUILDING_ADDRESS_TEST = "Building Sample Address";
    static final String BUILDING_NAME_TEST = "Building Sample Name";

    @BeforeEach
    void setUp() {
        building = Building.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();
        buildingDTO = BuildingDTO.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();

        floor = Floor.builder().id(7).building(building).floorNumber(13).build();
        floorDTO = FloorDTO.builder().id(7).building(buildingDTO).floorNumber(13).build();

        room = Room.builder().id(9).number(256).seatsNumber(8).isMultimediaAvailable(true).floor(floor).build();
        roomDTO = RoomDTO.builder().id(9).number(256).seatsNumber(8).isMultimediaAvailable(true).floor(floorDTO).build();
    }

    @Test
    @DisplayName("11.0.0 Check if Room entity can be converted to DTO correctly")
    void checkToDto() {
        assert(roomService.toDTO(room)).equals(roomDTO);
    }

}
