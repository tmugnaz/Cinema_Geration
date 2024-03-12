package org.slytherin.cinema.model.services.abstraction;

import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.User;

import java.util.List;

public interface FilmService {
    List<Film> findAllFilms();
    Film findFilmById(Long filmId);
    List<Film> findFilmsByGenre(String genre);
    void createFilm(Film film, User user);
    void deleteFilmById(long filmId, User user);
    void updateFilm(Film film, User user);
    List<Film> findTop10Films();
    Film findFilmByProjectionId(long projectionId);

}
