package org.slytherin.cinema.api.restcontrollers;
import org.slytherin.cinema.dtos.HallDto;
import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall")
@CrossOrigin
public class HallController {
    CinemaService cinemaService;
    @Autowired
    public HallController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<HallDto>> getAllHall() {
        List<Hall> halls = cinemaService.findAllHalls();
        List<HallDto> result = halls.stream().map(HallDto::new).toList();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getHallById(@PathVariable long id){
        Hall hall = cinemaService.findHallById(id);
        if (hall != null){
            HallDto result = new HallDto(hall);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}