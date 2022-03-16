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
            "where SUBSTRING(reservation.startTms, 1, 10) <= SUBSTRING(:reservationTms, 1, 10) " +
            "and SUBSTRING(:reservationTms, 1, 10) <= SUBSTRING(reservation.endTms, 1, 10) " +
            "and reservation.room.id = :roomId")
    List<Reservation> fetchAllReservationsByDate(
            @Param("reservationTms") Timestamp reservationTms,
            @Param("roomId") int roomId
    );

    @Query(value = "select reservation from Reservation reservation " +
            "where ((reservation.startTms >= :startTms and reservation.startTms < :endTms) " +
            "or (reservation.endTms > :startTms and reservation.endTms <= :endTms)" +
            "or (reservation.startTms <= :startTms and reservation.endTms >= :endTms)) " +
            "and reservation.room.id = :roomId " +
            "and reservation.reservationStatus.name not like 'CANCELLED'")
    List<Reservation> findActiveRoomReservationsInTimePeriod(
            @Param("startTms") Timestamp startTms,
            @Param("endTms") Timestamp endTms,
            @Param("roomId") int roomId
    );

}