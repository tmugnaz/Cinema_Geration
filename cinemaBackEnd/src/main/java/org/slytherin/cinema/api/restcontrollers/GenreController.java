package org.slytherin.cinema.api.restcontrollers;

import org.slytherin.cinema.dtos.ActorDto;
import org.slytherin.cinema.dtos.GenreDto;
import org.slytherin.cinema.model.entities.Actor;
import org.slytherin.cinema.model.entities.Genre;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@CrossOrigin
public class GenreController {
    CinemaService cinemaService;
    @Autowired
    public GenreController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<Genre> genres = cinemaService.findAllGenres();
        List<GenreDto> result = genres.stream().map(GenreDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
