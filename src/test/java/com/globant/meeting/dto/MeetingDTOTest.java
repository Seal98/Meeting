package com.globant.meeting.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
@Slf4j
public class MeetingDTOTest {

    MeetingDTO meetingDTO;

    @BeforeEach
    void setUp() {
        meetingDTO = MeetingDTO.builder()
                .roomId(3)
                .startDate(new Timestamp(123231231))
                .endDate(new Timestamp(334231231))
                .requiredSeatsNumber(15)
                .isMultimediaRequired(true)
                .build();
    }

    @Test
    @DisplayName("3.0.0 Check if Meeting DTO can be created correctly")
    void checkMeetingDTOCreationWithAllParams(){
        assert(meetingDTO.getRoomId() == 3);
        assert(meetingDTO.getStartDate().getTime() == 123231231);
        assert(meetingDTO.getEndDate().getTime() == 334231231);
        assert(meetingDTO.getRequiredSeatsNumber() == 15);
        assert(meetingDTO.isMultimediaRequired());
    }

    @Test
    @DisplayName("3.0.1 Check if Meeting DTO No Args constructor works as expected")
    void checkMeetingDTOCreationWithNoParams(){
        meetingDTO = new MeetingDTO();
        assert(meetingDTO.getRoomId() == 0);
        assert(meetingDTO.getStartDate() == null);
        assert(meetingDTO.getEndDate() == null);
        assert(meetingDTO.getRequiredSeatsNumber() == 0);
        assert(!meetingDTO.isMultimediaRequired());
    }

}