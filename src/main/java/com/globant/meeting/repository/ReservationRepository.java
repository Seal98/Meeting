package com.globant.meeting.repository;

import com.globant.meeting.entity.Reservation;
import com.globant.meeting.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findMeetingById(int meetingId);

    @Query(value = "select reservation from Reservation reservation " +
            "where SUBSTRING(reservation.startTs, 1, 10) <= SUBSTRING(:reservationTs, 1, 10) " +
            "and SUBSTRING(:reservationTs, 1, 10) <= SUBSTRING(reservation.endTs, 1, 10) " +
            "and reservation.room.id = :roomId")
    List<Reservation> fetchAllReservationsByDate(
            @Param("reservationTs") Timestamp reservationTs,
            @Param("roomId") int roomId
    );

    @Query(value = "select reservation from Reservation reservation " +
            "where ((reservation.startTs >= :startTs and reservation.startTs < :endTs) " +
            "or (reservation.endTs > :startTs and reservation.endTs <= :endTs)" +
            "or (reservation.startTs <= :startTs and reservation.endTs >= :endTs)) " +
            "and reservation.room.id = :roomId " +
            "and reservation.reservationStatus.name not like 'CANCELLED'")
    List<Reservation> findActiveRoomReservationsInTimePeriod(
            @Param("startTs") Timestamp startTs,
            @Param("endTs") Timestamp endTs,
            @Param("roomId") int roomId
    );

}