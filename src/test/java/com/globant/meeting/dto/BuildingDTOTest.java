package com.globant.meeting.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class BuildingDTOTest {

    BuildingDTO buildingDTO;

    @Test
    @DisplayName("1.0.0 Check if Building DTO can be created correctly")
    void checkBuildingDTOCreationWithAllParams(){
        buildingDTO = BuildingDTO.builder()
                .id(3)
                .name("Globant")
                .address("Prytyckaha 79")
                .build();
        assert(buildingDTO.getName()).equals("Globant");
        assert(buildingDTO.getAddress()).equals("Prytyckaha 79");
        assert(buildingDTO.getId() == 3);
    }

    @Test
    @DisplayName("1.0.1 Check if Building DTO No Args constructor works as expected")
    void checkBuildingDTOCreationWithNoParams(){
        buildingDTO = new BuildingDTO();
        assert(buildingDTO.getId() == 0);
        assert (buildingDTO.getName()) == null;
        assert (buildingDTO.getAddress()) == null;
    }

}