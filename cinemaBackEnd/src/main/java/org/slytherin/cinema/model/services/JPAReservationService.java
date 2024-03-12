package org.slytherin.cinema.model.services;

import jakarta.transaction.Transactional;
import org.slytherin.cinema.model.ReservationRequest;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Reservation;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.BusinessLogicExeception;
import org.slytherin.cinema.model.exceptions.DuplicateReservationException;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;
import org.slytherin.cinema.model.repositories.abstractions.FilmProjectionRepository;
import org.slytherin.cinema.model.repositories.abstractions.ReservationRepository;
import org.slytherin.cinema.model.services.abstraction.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class JPAReservationService implements ReservationService {

    private ReservationRepository reservationRepository;
    private FilmProjectionRepository filmProjectionRepository;

    public JPAReservationService(ReservationRepository reservationRepository, FilmProjectionRepository filmProjectionRepository) {
        this.reservationRepository = reservationRepository;
        this.filmProjectionRepository = filmProjectionRepository;
    }

    @Override
    @Transactional
    public Reservation createReservation(ReservationRequest reservationRequest, User user) throws EntityNotFoundException, DuplicateReservationException, BusinessLogicExeception {
        Optional<FilmProjection> ot = filmProjectionRepository.findById(reservationRequest.getIdProjection());
        System.out.println(reservationRequest.getIdProjection());
        if (ot.isEmpty()){
            throw new EntityNotFoundException("Tentativo di prenotare per una proiezione inesistente", FilmProjection.class);
        }
        FilmProjection filmProjection = ot.get();
        if (reservationRequest.getSeatNumber() > 0 && reservationRequest.getSeatNumber() > filmProjection.getHall().getTotalSeatNumber()){
            throw new BusinessLogicExeception
                    (String.format("Posto inesistente, hall id:%d seat id: %d",
                            filmProjection.getHallId(), reservationRequest.getSeatNumber()));
        }
        Optional<Reservation> result = reservationRepository.findByFilmProjectionIdEqualsAndReservedSeatEquals(reservationRequest.getIdProjection(),
                reservationRequest.getSeatNumber());
        if (result.isPresent()){
            throw new DuplicateReservationException("Tentativo di prenotare un posto già occupato",
                    reservationRequest.getSeatNumber(),
                    reservationRequest.getIdProjection(),
                    reservationRequest.isDiscount());
        }



        Reservation reservation = new Reservation(0,user, ot.get(),
                reservationRequest.getSeatNumber(), LocalDate.now(), LocalTime.now(), reservationRequest.isDiscount());

        reservationRepository.save(reservation);
        return reservation;
    }
}
