package com.globant.meeting.controller;


import com.globant.meeting.dto.MeetingDTO;
import com.globant.meeting.entity.Reservation;
import com.globant.meeting.entity.ReservationDTO;
import com.globant.meeting.service.ReservationService;
import com.globant.meeting.exception.ReservationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reservation/v1")
public class ReservationController {

    private static final String RESERVATIONS_FETCHING_DATE_FORMAT = "yyyy-MM-dd";

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @GetMapping("/fetchAllReservations")
    public List<ReservationDTO> fetchAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/fetchReservation/{reservationId}")
    public ReservationDTO fetchReservation(@PathVariable Integer reservationId){
        return reservationService.getReservationById(reservationId);
    }

    @GetMapping("/fetchAllReservationsByDate/{reservationDate}/{roomId}")
    public List<ReservationDTO> fetchAllReservations(@PathVariable
                                                     @DateTimeFormat(pattern = RESERVATIONS_FETCHING_DATE_FORMAT)
                                                             Date reservationDate,
                                                     @PathVariable int roomId){
        return reservationService.fetchAllReservationsByDate(new Timestamp(reservationDate.getTime()), roomId);
    }

    @PostMapping(value = "/reserveMeeting")
    public ResponseEntity<String> reserveMeeting(@RequestBody MeetingDTO requestData) {
        ResponseEntity<String> responseEntity;
        try {
            reservationService.reserveMeeting(requestData.getStartDate(), requestData.getEndDate(),
                    requestData.getRoomId(), requestData.getRequiredSeatsNumber(), requestData.isMultimediaRequired());
            responseEntity = new ResponseEntity<>("Reservation was successfully created", HttpStatus.CREATED);
        } catch(ReservationException e){
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

}