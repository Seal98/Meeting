package com.globant.meeting.repository;

import com.globant.meeting.entity.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationTypeRepository extends JpaRepository<ReservationType, Integer> {

    @Query("select type from ReservationType type where type.name like 'MEETING'")
    ReservationType getMeetingType();

    @Query("select type from ReservationType type where type.name like 'CLEANING'")
    ReservationType getCleaningType();

    @Query("select type from ReservationType type where type.name like 'MAINTENANCE'")
    ReservationType getMaintenanceType();

}
