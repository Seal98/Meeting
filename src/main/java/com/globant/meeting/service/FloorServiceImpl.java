package com.globant.meeting.service;

import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.entity.Floor;
import com.globant.meeting.entity.Room;
import com.globant.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FloorServiceImpl implements FloorService {

    private final BuildingService buildingService;

    @Override
    public FloorDTO toDTO(Floor floor) {
        return FloorDTO.builder()
                .id(floor.getId())
                .floorNumber(floor.getFloorNumber())
                .building(buildingService.toDTO(floor.getBuilding()))
                .build();
    }
}
