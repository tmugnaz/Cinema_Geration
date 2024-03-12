package org.slytherin.cinema.model.services.abstraction;

import org.slytherin.cinema.model.ReservationRequest;
import org.slytherin.cinema.model.entities.Reservation;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.BusinessLogicExeception;
import org.slytherin.cinema.model.exceptions.DuplicateReservationException;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;

public interface ReservationService {
    Reservation createReservation(ReservationRequest reservationRequest, User user) throws EntityNotFoundException, DuplicateReservationException, BusinessLogicExeception;
}
