package com.globant.meeting.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FloorDTOTest {

    FloorDTO floorDTO;

    BuildingDTO buildingDTO = new BuildingDTO();

    @BeforeEach
    void setUp() {
        floorDTO = FloorDTO.builder()
                .id(3)
                .floorNumber(15)
                .building(buildingDTO)
                .build();
    }

    @Test
    @DisplayName("2.0.0 Check if Floor DTO can be created correctly")
    void checkFloorDTOCreationWithAllParams(){
        assert(floorDTO.getId() == 3);
        assert(floorDTO.getFloorNumber() == 15);
        assert(floorDTO.getBuilding() == buildingDTO);
    }

    @Test
    @DisplayName("2.0.1 Check if Floor DTO No Args constructor works as expected")
    void checkFloorDTOCreationWithNoParams(){
        floorDTO = new FloorDTO();
        assert (floorDTO.getId() == 0);
        assert (floorDTO.getFloorNumber()) == 0;
        assert (floorDTO.getBuilding()) == null;
    }

}