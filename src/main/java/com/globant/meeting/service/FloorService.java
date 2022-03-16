package com.globant.meeting.service;

import com.globant.meeting.dto.FloorDTO;
import com.globant.meeting.entity.Floor;
import com.globant.meeting.entity.Room;

import java.util.Date;
import java.util.List;

public interface FloorService {

    FloorDTO toDTO(Floor floor);

}
