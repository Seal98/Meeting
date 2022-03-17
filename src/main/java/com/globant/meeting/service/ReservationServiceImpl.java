package com.globant.meeting.service;

import com.globant.meeting.entity.Reservation;
import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.entity.Room;
import com.globant.meeting.repository.ReservationRepository;
import com.globant.meeting.repository.ReservationStatusRepository;
import com.globant.meeting.repository.ReservationTypeRepository;
import com.globant.meeting.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {

    private final RoomService roomService;
    private final ReservationRepository reservationRepository;
    private final ReservationStatusRepository reservationStatusRepository;
    private final ReservationTypeRepository reservationTypeRepository;

    @Transactional
    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReservationDTO getReservationById(int meetingId) {
        return this.toDTO(reservationRepository.findMeetingById(meetingId));
    }

    @Transactional
    @Override
    public ReservationDTO reserveMeeting(Timestamp startTs, Timestamp endTs, int roomId, int requiredSeatsNumber,
                               boolean isMultimediaRequired) throws ReservationException {
        Reservation reservation;
        Room room = roomService.findEligibleRoom(roomId, requiredSeatsNumber, isMultimediaRequired);
        if(room == null){
            throw new ReservationException("Required room doesn't match the provided conditions.");
        }

        List<Reservation> overlappedReservations = reservationRepository
                .findActiveRoomReservationsInTimePeriod(startTs, endTs, roomId);

        if(overlappedReservations.isEmpty()){
            reservation = reservationRepository.save(Reservation.builder()
                    .startTs(startTs)
                    .reservationType(reservationTypeRepository.getMeetingType())
                    .reservationStatus(reservationStatusRepository.getReservedStatus())
                    .endTs(endTs)
                    .room(room)
                    .build());
        } else {
            throw new ReservationException("The selected time interval is not available for the reservation.");
        }
        return this.toDTO(reservation);
    }

    @Override
    public List<ReservationDTO> fetchAllReservationsByDate(Timestamp reservationTs, int roomId) {
        return reservationRepository.fetchAllReservationsByDate(reservationTs, roomId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO toDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .startTs(reservation.getStartTs())
                .endTs(reservation.getEndTs())
                .reservationType(reservation.getReservationType())
                .reservationStatus(reservation.getReservationStatus())
                .room(roomService.toDTO(reservation.getRoom()))
                .build();
    }

}
