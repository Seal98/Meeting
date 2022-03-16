package com.globant.meeting.entity;

import com.globant.meeting.dto.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private int id;

    private Timestamp startTms;

    private Timestamp endTms;

    private ReservationType reservationType;

    private ReservationStatus reservationStatus;

    private RoomDTO room;
}