package org.slytherin.cinema.api.restcontrollers;
import org.slytherin.cinema.dtos.FeedbackDto;
import org.slytherin.cinema.dtos.FilmDetailsDto;
import org.slytherin.cinema.dtos.FilmDto;
import org.slytherin.cinema.model.entities.Feedback;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.Performance;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.FilmNotFoundException;
import org.slytherin.cinema.model.exceptions.UnauthorizedOperationException;
import org.slytherin.cinema.model.services.JPACinemaService;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.slytherin.cinema.model.services.abstraction.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/film")
@CrossOrigin
public class FilmController {
    FilmService filmService;
    CinemaService cinemaService;

    @Autowired
    public FilmController(FilmService filmService,CinemaService cinemaService) {
        this.filmService = filmService;
        this.cinemaService= cinemaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        List<Film> films = filmService.findAllFilms();
        List<FilmDto> result = films.stream().map(FilmDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDetailsDto> getFilmById(@PathVariable long id){
        Film film = filmService.findFilmById(id);
        if (film != null){
            FilmDetailsDto result = new FilmDetailsDto(film);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<FilmDto>> getFilmsByGenre(@RequestParam String genre){
        List<Film> films = filmService.findFilmsByGenre(genre);
        List<FilmDto> result = films.stream().map(FilmDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<FilmDto>> getTop10Films() {
        List<Film> top10Films = filmService.findTop10Films();
        List<FilmDto> result = top10Films.stream().map(FilmDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{filmId}/feedback")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByFilmId(@PathVariable long filmId){
        List<Feedback> fd = cinemaService.findAllFeedbackByFilmId(filmId);

        List<FeedbackDto> fdo = fd.stream().map(FeedbackDto:: new).toList();
        return ResponseEntity.ok(fdo);
    }

    @GetMapping("/{projectionId}/projection")
    public ResponseEntity<FilmDetailsDto> getFilmDetailsByProjectionId(@PathVariable long projectionId){
        Film film = filmService.findFilmByProjectionId(projectionId);
        FilmDetailsDto filmDetailsDto = new FilmDetailsDto(film);
        return ResponseEntity.ok(filmDetailsDto);
    }

}
