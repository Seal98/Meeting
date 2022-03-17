package com.globant.meeting.repository;

import com.globant.meeting.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("select room from Room room where " +
            "room.id = :roomId and " +
            "room.seatsNumber >= :requiredSeatsNumber and " +
            "(room.isMultimediaAvailable = TRUE or room.isMultimediaAvailable = :isMultimediaRequired)")
    Room findEligibleRoom(
            @Param("roomId") int roomId,
            @Param("requiredSeatsNumber") int requiredSeatsNumber,
            @Param("isMultimediaRequired") boolean isMultimediaRequired
    );

    @Query("select room from Room room where " +
            "room not in (select reservation.room from Reservation reservation " +
            "where ((reservation.startTs >= :startTs and reservation.startTs < :endTs) " +
            "or (reservation.endTs > :startTs and reservation.endTs <= :endTs)" +
            "or (reservation.startTs <= :startTs and reservation.endTs >= :endTs)) " +
            "and reservation.reservationStatus.name not like 'CANCELLED') " +
            "and (room.isMultimediaAvailable = TRUE or room.isMultimediaAvailable = :isMultimediaRequired) " +
            "and room.seatsNumber >= :requiredSeatsNumber " +
            "order by room.seatsNumber asc, room.isMultimediaAvailable asc")
    List<Room> findRoomsByConditions(
            @Param("requiredSeatsNumber") int requiredSeatsNumber,
            @Param("isMultimediaRequired") boolean isMultimediaRequired,
            @Param("startTs") Timestamp startTs,
            @Param("endTs") Timestamp endTs
    );

}