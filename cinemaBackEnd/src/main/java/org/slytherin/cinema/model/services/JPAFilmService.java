package org.slytherin.cinema.model.services;

import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.repositories.abstractions.FilmProjectionRepository;
import org.slytherin.cinema.model.repositories.abstractions.FilmRepository;
import org.slytherin.cinema.model.services.abstraction.FilmService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class JPAFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmProjectionRepository filmProjectionRepository;

    public JPAFilmService(FilmRepository filmRepository, FilmProjectionRepository filmProjectionRepository) {
        this.filmRepository = filmRepository;
        this.filmProjectionRepository = filmProjectionRepository;
    }
    @Override
    public void createFilm(Film film, User user) {
        filmRepository.save(film);
    }
    @Override
    public void deleteFilmById(long filmId, User user) {
        filmRepository.deleteById(filmId);
    }
    @Override
    public void updateFilm(Film film, User user) {
        filmRepository.save(film);
    }

    @Override
    public List<Film> findTop10Films() {
        return filmRepository.findTop10ByAverage();
    }

    @Override
    public Film findFilmByProjectionId(long projectionId) {
        Optional<FilmProjection> projection = filmProjectionRepository.findById(projectionId);
        return projection.map(filmProjection -> filmRepository.findById(filmProjection.getFilm().getId())).orElse(null);
    }

    @Override
    public Film findFilmById(Long filmId) {
        Optional<Film> ot = filmRepository.findById(filmId);
        return ot.orElse(null);
    }

    @Override
    public List<Film> findFilmsByGenre(String genre) {
        return filmRepository.findByGenresGenreName(genre);
    }

    @Override
    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }
}
