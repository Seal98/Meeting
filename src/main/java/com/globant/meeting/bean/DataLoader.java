package com.globant.meeting.bean;

import com.globant.meeting.entity.*;
import com.globant.meeting.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final BuildingRepository buildingRepository;
    private final FloorRepository floorRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTypeRepository reservationTypeRepository;
    private final ReservationStatusRepository reservationStatusRepository;

    public DataLoader(BuildingRepository buildingRepository, FloorRepository floorRepository, RoomRepository roomRepository, ReservationRepository reservationRepository, ReservationTypeRepository reservationTypeRepository, ReservationStatusRepository reservationStatusRepository) {
        this.buildingRepository = buildingRepository;
        this.floorRepository = floorRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.reservationTypeRepository = reservationTypeRepository;
        this.reservationStatusRepository = reservationStatusRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        loadDataObjects();
    }

    private void loadDataObjects() throws ParseException {
        if(buildingRepository.count() == 0){
            log.info("Create default data");

            Building building = Building.builder()
                    .name("Globant Bel")
                    .build();
            buildingRepository.save(building);

            Floor floor1 = createFloor(1, building);
            Floor floor2 = createFloor(2, building);
            Floor floor3 = createFloor(3, building);
            floorRepository.save(floor1);
            floorRepository.save(floor2);
            floorRepository.save(floor3);

            Room room1 = createRoom(1, floor1, 3, true);
            Room room2 = createRoom(2, floor2, 5, false);
            Room room3 = createRoom(3, floor1, 10, true);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            ReservationType reservationTypeMeeting = createReservationType("Meeting", "MEETING");
            ReservationType reservationTypeCleaning = createReservationType("Cleaning", "CLEANING");
            ReservationType reservationTypeMaintenance = createReservationType("Maintenance", "MAINTENANCE");
            reservationTypeRepository.save(reservationTypeMeeting);
            reservationTypeRepository.save(reservationTypeCleaning);
            reservationTypeRepository.save(reservationTypeMaintenance);
            ReservationStatus reservationStatusReserved = createReservationStatus("Reserved", "RESERVED");
            ReservationStatus reservationStatusCancelled = createReservationStatus("Cancelled", "CANCELLED");
            ReservationStatus reservationStatusFinished = createReservationStatus("Finished", "FINISHED");
            reservationStatusRepository.save(reservationStatusReserved);
            reservationStatusRepository.save(reservationStatusCancelled);
            reservationStatusRepository.save(reservationStatusFinished);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

            createAndSaveAllRoomsCleaningReservationsForOneMonth(reservationTypeCleaning, reservationStatusReserved);

            Reservation reservation1 = createReservation(room1, Timestamp.valueOf("2022-04-13 02:00:00"), Timestamp.valueOf("2022-04-13 02:30:00"), reservationTypeMeeting, reservationStatusFinished);
            Reservation reservation2 = createReservation(room1, Timestamp.valueOf("2023-04-15 04:00:00"), Timestamp.valueOf("2023-04-15 04:15:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation3 = createReservation(room1, Timestamp.valueOf("2023-04-15 15:00:00"), Timestamp.valueOf("2023-04-15 15:30:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation4 = createReservation(room1, Timestamp.valueOf("2023-04-15 17:00:00"), Timestamp.valueOf("2023-04-15 17:45:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation5 = createReservation(room1, Timestamp.valueOf("2023-04-15 19:00:00"), Timestamp.valueOf("2023-04-15 21:15:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation6 = createReservation(room1, Timestamp.valueOf("2023-04-16 04:00:00"), Timestamp.valueOf("2023-04-15 04:15:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation7 = createReservation(room1, Timestamp.valueOf("2023-04-18 03:00:00"), Timestamp.valueOf("2023-04-15 04:15:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation8 = createReservation(room1, Timestamp.valueOf("2023-04-21 07:45:00"), Timestamp.valueOf("2023-04-15 09:00:00"), reservationTypeMeeting, reservationStatusReserved);
            Reservation reservation9 = createReservation(room1, Timestamp.valueOf("2023-04-22 04:00:00"), Timestamp.valueOf("2023-04-15 04:20:00"), reservationTypeMeeting, reservationStatusReserved);
            reservationRepository.save(reservation1);
            reservationRepository.save(reservation2);
            reservationRepository.save(reservation3);
            reservationRepository.save(reservation4);
            reservationRepository.save(reservation5);
            reservationRepository.save(reservation6);
            reservationRepository.save(reservation7);
            reservationRepository.save(reservation8);
            reservationRepository.save(reservation9);
        }
    }

    /**
     * Could be started by a scheduler
     */
    private void createAndSaveAllRoomsCleaningReservationsForOneMonth(ReservationType reservationType,
                                                                      ReservationStatus reservationStatus){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        Calendar cal = Calendar.getInstance();

        List<Timestamp> listOfCleaningStartDates = startDate.datesUntil(endDate)
                .map(cleaningLocalDate -> Timestamp.from(cleaningLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .map(cleaningDate -> {
                    cal.setTime(cleaningDate);
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    return new Timestamp(cal.getTime().getTime());
                })
                .collect(Collectors.toList());

        List<Room> allRooms = roomRepository.findAll();
        allRooms.forEach(room -> {
            listOfCleaningStartDates.forEach(cleaningStartDate -> {
                cal.setTime(cleaningStartDate);
                cal.set(Calendar.MINUTE, 5 + room.getSeatsNumber());
                reservationRepository.save(createReservation(room, new Timestamp(cleaningStartDate.getTime()),
                        new Timestamp(cal.getTime().getTime()), reservationType,
                        reservationStatus));
            });
        });
    }

    private Room createRoom(int roomNumber, Floor floor, int seatsNumber, boolean isMultimediaAvailable) {
        return Room.builder()
                .number(roomNumber)
                .seatsNumber(seatsNumber)
                .isMultimediaAvailable(isMultimediaAvailable)
                .floor(floor)
                .build();
    }

    private Floor createFloor(int floorNumber, Building building) {
        return Floor.builder()
                .floorNumber(floorNumber)
                .building(building)
                .build();
    }

    private Reservation createReservation(Room room, Timestamp startTms, Timestamp endTms, ReservationType reservationType,
                                          ReservationStatus reservationStatus) {
        return Reservation.builder()
                .room(room)
                .startTms(startTms)
                .endTms(endTms)
                .reservationType(reservationType)
                .reservationStatus(reservationStatus)
                .build();
    }

    private ReservationType createReservationType(String typeLabel, String typeName) {
        return ReservationType.builder()
                .label(typeLabel)
                .name(typeName)
                .build();
    }

    private ReservationStatus createReservationStatus(String statusLabel, String statusName) {
        return ReservationStatus.builder()
                .label(statusLabel)
                .name(statusName)
                .build();
    }
}