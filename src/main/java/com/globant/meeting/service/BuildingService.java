package com.globant.meeting.service;

import com.globant.meeting.dto.BuildingDTO;
import com.globant.meeting.entity.Building;

public interface BuildingService {

    BuildingDTO toDTO(Building building);

}
