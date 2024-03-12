package org.slytherin.cinema.model.services.abstraction;

import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FilmProjectionService {
    Optional<FilmProjection> findFilmProjectionById(Long filmProjectionId);
    List<FilmProjection> findAllProjections();
    List<FilmProjection> getPastProjections();
    List<FilmProjection> getFilmProjectionForToday();
    List<FilmProjection> getFilmProjectionByFilmAndDate(Long filmId, LocalDate date);
    List<FilmProjection> getFilmProjectionNextWeek();
    void createProjection(FilmProjection projection, User user);
    void deleteProjectionById(long projectionId, User user);
    void updateProjection(FilmProjection projection, User user);

    Hall findHallByProjectionId(long projectionId) throws EntityNotFoundException;
    List<FilmProjection> findAll();
//    List<FilmProjection> getAllByHall(long id);
}
