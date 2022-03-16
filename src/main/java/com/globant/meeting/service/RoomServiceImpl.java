package com.globant.meeting.service;

import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.Room;
import com.globant.meeting.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final FloorService floorService;

    @Override
    public Room findEligibleRoom(int roomId, int requiredSeatsNumber, boolean isMultimediaRequired) {
        return roomRepository.findEligibleRoom(roomId, requiredSeatsNumber, isMultimediaRequired);
    }

    @Override
    public List<RoomDTO> getRoomsByConditions(int requiredSeatsNumber, boolean isMultimediaRequired, Timestamp startDate, Timestamp endDate) {
        return roomRepository.findRoomsByConditions(requiredSeatsNumber, isMultimediaRequired, startDate, endDate)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoomDTO toDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .seatsNumber(room.getSeatsNumber())
                .isMultimediaAvailable(room.isMultimediaAvailable())
                .floor(floorService.toDTO(room.getFloor()))
                .build();
    }

}
