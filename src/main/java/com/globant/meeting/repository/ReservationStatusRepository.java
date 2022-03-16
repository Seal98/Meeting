package com.globant.meeting.repository;

import com.globant.meeting.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Integer> {

    @Query("select status from ReservationStatus status where status.name like 'RESERVED'")
    ReservationStatus getReservedStatus();

    @Query("select status from ReservationStatus status where status.name like 'CANCELLED'")
    ReservationStatus getCancelledStatus();

    @Query("select status from ReservationStatus status where status.name like 'FINISHED'")
    ReservationStatus getFinishedStatus();

}
