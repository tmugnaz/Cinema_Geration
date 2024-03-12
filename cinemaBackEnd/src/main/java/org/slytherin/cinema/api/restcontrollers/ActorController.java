package org.slytherin.cinema.api.restcontrollers;
import org.slytherin.cinema.dtos.ActorDto;
import org.slytherin.cinema.dtos.FilmDto;
import org.slytherin.cinema.dtos.FilmProjectionDto;
import org.slytherin.cinema.model.entities.Actor;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/actor")
@CrossOrigin
public class ActorController {
    CinemaService cinemaService;
    @Autowired
    public ActorController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<ActorDto>> getAllActors() {
        List<Actor> actors = cinemaService.findAllActors();
        List<ActorDto> result = actors.stream().map(ActorDto::new).toList();
        return ResponseEntity.ok(result);
    }
}
