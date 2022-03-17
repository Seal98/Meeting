package com.globant.meeting.service;

import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.entity.Room;
import com.globant.meeting.exception.ReservationException;
import com.globant.meeting.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Optional;


@SpringBootTest
@Slf4j
public class RoomServiceImplTest {

    @Autowired
    RoomService roomService;

    @Autowired
    RoomRepository roomRepository;

    Room room;

    @BeforeEach
    void setUp() {
        Optional<Room> result = roomRepository.findById(5);
        room = result.orElse(null);
    }

    @Test
    @DisplayName("10.0.0 Check if the room eligibility check by input params works as expected")
    void checkIfEligibleRoom() {
        assert roomService.findEligibleRoom(room.getId(), room.getSeatsNumber(), room.isMultimediaAvailable())
                .equals(room);
    }

}
