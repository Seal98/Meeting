package com.globant.meeting.controller;


import com.globant.meeting.dto.RoomDTO;
import com.globant.meeting.entity.Room;
import com.globant.meeting.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/room/v1")
public class RoomController {

    private static final String ROOMS_FETCHING_WITH_CONDITIONS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/fetchRoomsByConditions/{requiredSeatsNumber}/{isMultimediaRequired}/{startDate}/{endDate}")
    public List<RoomDTO> fetchRoomsByConditions(@PathVariable int requiredSeatsNumber,
                                                @PathVariable boolean isMultimediaRequired,
                                                @PathVariable
                                                @DateTimeFormat(pattern = ROOMS_FETCHING_WITH_CONDITIONS_DATE_FORMAT)
                                                        Date startDate,
                                                @PathVariable
                                                @DateTimeFormat(pattern = ROOMS_FETCHING_WITH_CONDITIONS_DATE_FORMAT)
                                                        Date endDate){
        return roomService.getRoomsByConditions(requiredSeatsNumber, isMultimediaRequired,
                new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()));
    }

}