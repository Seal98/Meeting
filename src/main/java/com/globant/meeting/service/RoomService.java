package com.globant.meeting.service;

import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.Room;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface RoomService {

    Room findEligibleRoom(int roomId, int requiredSeatsNumber, boolean isMultimediaRequired);

    List<RoomDTO> getRoomsByConditions(int requiredSeatsNumber, boolean isMultimediaRequired, Timestamp startDate, Timestamp endDate);

    RoomDTO toDTO(Room room);

}
