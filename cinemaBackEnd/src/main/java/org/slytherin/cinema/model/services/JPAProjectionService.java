package org.slytherin.cinema.model.services;

import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;
import org.slytherin.cinema.model.exceptions.ProjectionNotFoundException;
import org.slytherin.cinema.model.repositories.abstractions.FilmProjectionRepository;
import org.slytherin.cinema.model.repositories.abstractions.HallRepository;
import org.slytherin.cinema.model.services.abstraction.FilmProjectionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class JPAProjectionService implements FilmProjectionService {
    private final FilmProjectionRepository filmProjectionRepository;
    private final HallRepository hallRepository;

    public JPAProjectionService(FilmProjectionRepository filmProjectionRepository, HallRepository hallRepository) {

        this.filmProjectionRepository = filmProjectionRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public List<FilmProjection> getFilmProjectionForToday(){
        LocalDate today = LocalDate.now();
        return filmProjectionRepository.findByProjectionDate(today);
    }

    @Override
    public List<FilmProjection> getFilmProjectionByFilmAndDate(Long filmId, LocalDate date) {
        return filmProjectionRepository.findByFilmIdAndProjectionDate(filmId, date);
    }

    @Override
    public List<FilmProjection> getFilmProjectionNextWeek() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        return filmProjectionRepository.findByProjectionDateBetween(startDate, endDate);
    }

    @Override

    public void createProjection(FilmProjection projection, User user) {
        filmProjectionRepository.save(projection);
    }

    @Override
    public void deleteProjectionById(long projectionId, User user) {
        filmProjectionRepository.deleteById(projectionId);
    }

    @Override

    public void updateProjection(FilmProjection projection, User user) {
        filmProjectionRepository.save(projection);
    }

    @Override
    public Hall findHallByProjectionId(long projectionId) throws EntityNotFoundException {
        Optional<FilmProjection> filmProjection = filmProjectionRepository.findById(projectionId);
        if(filmProjection.isEmpty()){
            throw new EntityNotFoundException("Tentativo di ricerca sala su una proiezione inesistente", FilmProjection.class);
        }
         return hallRepository.findByProjectionId(projectionId);
    }

//    @Override
//    public List<FilmProjection> getAllByHall(long id) {
//        Optional<Hall> hall = hallRepository.findById(id);
//        if(hall.isPresent()){
//            return filmProjectionRepository.findByHall(hall.get().getId());
//        }
//        return null;
//    }


    @Override
    public List<FilmProjection> findAll() {
        return filmProjectionRepository.findAll();
    }



    @Override
    public List<FilmProjection> findAllProjections() {
        return filmProjectionRepository.findAll();
    }

    @Override
    public List<FilmProjection> getPastProjections() {
        LocalDate today = LocalDate.now();
        return filmProjectionRepository.findAll()
                .stream()
                .filter(pj -> pj.getProjectionDate().isBefore(today))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<FilmProjection> findFilmProjectionById(Long filmProjectionId) {
        Optional<FilmProjection> op = filmProjectionRepository.findById(filmProjectionId);
        return op;
    }
}
