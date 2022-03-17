package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.entity.Building;
import com.globant.meeting.entity.Floor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FloorServiceToDTOTest {

    @Autowired
    FloorService floorService;

    Building building;
    BuildingDTO buildingDTO;

    Floor floor;
    FloorDTO floorDTO;

    static final String BUILDING_ADDRESS_TEST = "Building Sample Address";
    static final String BUILDING_NAME_TEST = "Building Sample Name";

    @BeforeEach
    void setUp() {
        building = Building.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();
        buildingDTO = BuildingDTO.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();

        floor = Floor.builder().id(7).building(building).floorNumber(13).build();
        floorDTO = FloorDTO.builder().id(7).building(buildingDTO).floorNumber(13).build();
    }

    @Test
    @DisplayName("7.0.0 Check if Floor entity can be converted to DTO correctly")
    void checkToDto() {
        assert(floorService.toDTO(floor)).equals(floorDTO);
    }

}
