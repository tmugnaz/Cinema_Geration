package org.slytherin.cinema.model.services.abstraction;

import org.slytherin.cinema.model.entities.*;

import java.util.List;
import java.util.Optional;

public interface CinemaService {
    List<Actor> findAllActors();
    List<Genre> findAllGenres();
    List<Hall> findAllHalls();
    List<Actor> findActorsByFilmId(long filmId);

    Hall findHallById(long hallId);

    List<Reservation> findReservationByProjection(long projectionId);
    List<Feedback> findFeedbackByUser(Long userId);
    List<Reservation> findReservationByUser(Long userId);
    void createFeedback(Feedback feedback, User user);
    List<Feedback> findAllFeedbackByFilmId(long filmId);

}
