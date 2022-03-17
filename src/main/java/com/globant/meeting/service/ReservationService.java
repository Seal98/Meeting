package com.globant.meeting.service;

import com.globant.meeting.entity.Reservation;
import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.exception.ReservationException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ReservationService {

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservationById(int meetingId);

    ReservationDTO reserveMeeting(Timestamp startTs, Timestamp endTs, int roomId, int requiredSeatsNumber,
                        boolean isMultimediaRequired) throws ReservationException;

    List<ReservationDTO> fetchAllReservationsByDate(Timestamp reservationTs, int roomId);

    ReservationDTO toDTO(Reservation reservation);

}
