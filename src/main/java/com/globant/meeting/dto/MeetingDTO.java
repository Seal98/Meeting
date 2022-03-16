package com.globant.meeting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDTO {

    private int roomId;

    private Timestamp startDate;

    private Timestamp endDate;

    private int requiredSeatsNumber;

    @JsonProperty
    private boolean isMultimediaRequired;
}