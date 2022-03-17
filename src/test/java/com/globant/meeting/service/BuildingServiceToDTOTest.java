package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.entity.Building;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BuildingServiceToDTOTest {

    @Autowired
    BuildingService buildingService;

    Building building;

    BuildingDTO buildingDTO;

    static final String BUILDING_ADDRESS_TEST = "Building Sample Address";
    static final String BUILDING_NAME_TEST = "Building Sample Name";

    @BeforeEach
    void setUp() {
        building = Building.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();
        buildingDTO = BuildingDTO.builder().id(5).address(BUILDING_ADDRESS_TEST).name(BUILDING_NAME_TEST).build();
    }

    @Test
    void checkToDto() {
        assert(buildingService.toDTO(building)).equals(buildingDTO);
    }

}
