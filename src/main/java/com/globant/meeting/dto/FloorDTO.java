package com.globant.meeting.dto;

import com.globant.meeting.entity.Building;
import com.globant.meeting.entity.Room;
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
public class FloorDTO {

    private int id;

    private int floorNumber;

    private BuildingDTO building;

}