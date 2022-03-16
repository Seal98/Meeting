package com.globant.meeting.dto;

import com.globant.meeting.entity.Floor;
import com.globant.meeting.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    private int id;

    private int number;

    private boolean isMultimediaAvailable;

    private int seatsNumber;

    private FloorDTO floor;

}