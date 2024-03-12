package org.slytherin.cinema.api.restcontrollers;

import org.slytherin.cinema.dtos.*;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;
import org.slytherin.cinema.model.exceptions.ProjectionNotFoundException;
import org.slytherin.cinema.model.exceptions.UnauthorizedOperationException;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.slytherin.cinema.model.services.abstraction.FilmProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projection")
@CrossOrigin
public class FilmProjectionController {
    FilmProjectionService filmProjectionService;

    @Autowired

    public FilmProjectionController(FilmProjectionService filmProjectionService) {
        this.filmProjectionService = filmProjectionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FilmProjectionDto>> getAllProjection() {
        List<FilmProjection> films = filmProjectionService.findAllProjections();
        List<FilmProjectionDto> result = films.stream().map(FilmProjectionDto::new).toList();
        return ResponseEntity.ok(result);
    }

    /* ---------------------- METODI CRUD ---------------------- */
    @PostMapping("/")
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('manager:create')")
    public ResponseEntity<FilmProjectionDto> createProjection(@RequestBody FilmProjectionDto projectionDto,
                                                              @AuthenticationPrincipal User user) throws URISyntaxException {
        try {
            FilmProjection projection = projectionDto.fromDto();
            filmProjectionService.createProjection(projection, user);
            FilmProjectionDto result = new FilmProjectionDto(projection);
            URI location = new URI("/api/projection/" + result.getId());
            return ResponseEntity.created(location).body(result);
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('manager:delete')")
    public ResponseEntity<Void> deleteProjection(@PathVariable long id, @AuthenticationPrincipal User user) {
        try {
            filmProjectionService.deleteProjectionById(id, user);
            return ResponseEntity.noContent().build();
        } catch (ProjectionNotFoundException | UnauthorizedOperationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    public ResponseEntity<Void> updateFilm(@PathVariable long id,
                                           @RequestBody FilmProjection updatedProjection,
                                           @AuthenticationPrincipal User user) {
        try {
            Optional<FilmProjection> existingProjection = filmProjectionService.findFilmProjectionById(id);
            if (existingProjection.isPresent()) {
                updatedProjection.setId(id);
                filmProjectionService.updateProjection(updatedProjection, user);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(403).build();
        }
    }
    /* --------------------------------------------------------- */

    @GetMapping("/today")
    public ResponseEntity<List<DailyProjectionDto>> getFilmProjectionForToday(){
        List<FilmProjection> filmProjectionList = filmProjectionService.getFilmProjectionForToday();
        List<DailyProjectionDto> result = filmProjectionList.stream().map(DailyProjectionDto::new).toList();
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/{filmId}/today")
//    public ResponseEntity<List<DailyProjectionDto>> getFilmProjectionByFilmForToday(@PathVariable String filmId){
//        try {
//            Long id = Long.parseLong(filmId);
//            LocalDate today = LocalDate.now();
//            List<FilmProjection> filmProjectionList = cinemaService.getFilmProjectionByFilmAndDate(id, today);
//            List<DailyProjectionDto> result = filmProjectionList.stream().map(DailyProjectionDto::new).toList();
//            return ResponseEntity.ok(result);
//        } catch (NumberFormatException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmProjectionDto> getFilmProjectionById(@PathVariable long id){
        Optional<FilmProjection> filmProjection = filmProjectionService.findFilmProjectionById(id);
        if (filmProjection.isPresent()){
            FilmProjectionDto result = new FilmProjectionDto(filmProjection.get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/next-week")
    public ResponseEntity<List<FilmProjectionDto>> getFilmProjectionNextWeek() {
        List<FilmProjection> filmProjectionList = filmProjectionService.getFilmProjectionNextWeek();
        List<FilmProjectionDto> result = filmProjectionList.stream().map(FilmProjectionDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/past")
    public ResponseEntity<List<FilmProjectionDto>> getPastProjections(){
        List<FilmProjection> pastProjections = filmProjectionService.getPastProjections();
        List<FilmProjectionDto> result = pastProjections.stream().map(FilmProjectionDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/hall")
    public ResponseEntity<HallDto> findHallByProjectionId(@PathVariable("id") long projectionId){
        try {
            Hall hall = filmProjectionService.findHallByProjectionId(projectionId);
            HallDto dto = new HallDto(hall);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/{id}/projections-hall")
//    public ResponseEntity<List<DailyProjectionDto>> findProjectionsByHallId(@PathVariable("id") long hallId) throws EntityNotFoundException {
//        List<FilmProjection> projection = filmProjectionService.getAllByHall(hallId);
//        List<DailyProjectionDto> dto = projection.stream().map(DailyProjectionDto::new).toList();
//        return ResponseEntity.ok(dto);
//    }

}
