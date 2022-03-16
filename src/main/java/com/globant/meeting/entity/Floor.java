package com.globant.meeting.entity;

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
@Entity
@Table(name = "FLOOR")
public class Floor {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "FLOOR_NUMBER")
    private int floorNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUILDING_ID", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "floor", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Room> rooms;


}