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
    public void reserveMeeting(Timestamp startDate, Timestamp endDate, int roomId, int requiredSeatsNumber,
                               boolean isMultimediaRequired) throws ReservationException {
        Room room = roomService.findEligibleRoom(roomId, requiredSeatsNumber, isMultimediaRequired);
        if(room == null){
            throw new ReservationException("Required room doesn't match the provided conditions.");
        }

        List<Reservation> overlappedReservations = reservationRepository
                .findActiveRoomReservationsInTimePeriod(startDate, endDate, roomId);

        if(overlappedReservations.isEmpty()){
            reservationRepository.save(Reservation.builder()
                    .startTms(startDate)
                    .reservationType(reservationTypeRepository.getMeetingType())
                    .reservationStatus(reservationStatusRepository.getReservedStatus())
                    .endTms(endDate)
                    .room(room)
                    .build());
        } else {
            throw new ReservationException("The selected time interval is not available for the reservation.");
        }
    }

    @Override
    public List<ReservationDTO> fetchAllReservationsByDate(Timestamp reservationTms, int roomId) {
        return reservationRepository.fetchAllReservationsByDate(reservationTms, roomId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO toDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .startTms(reservation.getStartTms())
                .endTms(reservation.getEndTms())
                .reservationType(reservation.getReservationType())
                .reservationStatus(reservation.getReservationStatus())
                .room(roomService.toDTO(reservation.getRoom()))
                .build();
    }

}
