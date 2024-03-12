package org.slytherin.cinema.model.services;

import org.slytherin.cinema.model.entities.*;
import org.slytherin.cinema.model.repositories.abstractions.*;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JPACinemaService implements CinemaService {
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final HallRepository hallRepository;
    private final ReservationRepository reservationRepository;
    private final FeedbackRepository feedbackRepository;
    private final PerformanceRepository performanceRepository;
    private final FilmRepository filmRepository;

    public JPACinemaService(ActorRepository actorRepository,
                            HallRepository hallRepository,
                            GenreRepository genreRepository,
                            ReservationRepository reservationRepository,
                            FeedbackRepository feedbackRepository, PerformanceRepository performanceRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
        this.hallRepository = hallRepository;
        this.genreRepository = genreRepository;
        this.reservationRepository = reservationRepository;
        this.feedbackRepository = feedbackRepository;
        this.performanceRepository = performanceRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Actor> findActorsByFilmId(long filmId) {
        return actorRepository.findByFilmIdEquals(filmId);
    }


    @Override
    public Hall findHallById(long hallId) {
        Optional<Hall> ot = hallRepository.findById(hallId);
        return ot.orElse(null);
    }

    @Override
    public List<Reservation> findReservationByProjection(long projectionId) {
        return reservationRepository.findByFilmProjectionIdEquals(projectionId);
    }

    @Override
    public List<Feedback> findFeedbackByUser(Long userId) {
        return feedbackRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> findReservationByUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public void createFeedback(Feedback feedback, User user) {
       feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAllFeedbackByFilmId(long filmId) {
        return feedbackRepository.findByFilmId(filmId);
    }




    @Override
    public List<Actor> findAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Hall> findAllHalls() {
        return hallRepository.findAll();
    }

}
