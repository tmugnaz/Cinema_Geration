package org.slytherin.cinema.api.restcontrollers;
import org.slytherin.cinema.dtos.ProjectionReservationDto;
import org.slytherin.cinema.dtos.ReservationDto;
import org.slytherin.cinema.model.ReservationRequest;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Reservation;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.BusinessLogicExeception;
import org.slytherin.cinema.model.exceptions.DuplicateReservationException;
import org.slytherin.cinema.model.exceptions.EntityNotFoundException;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.slytherin.cinema.model.services.abstraction.FilmProjectionService;
import org.slytherin.cinema.model.services.abstraction.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservation")
@CrossOrigin
public class ReservationController {
CinemaService cinemaService;
ReservationService reservationService;
FilmProjectionService filmProjectionService;
    @Autowired
    public ReservationController(CinemaService cinemaService,
                                 ReservationService reservationService,
                                 FilmProjectionService filmProjectionService) {
        this.cinemaService = cinemaService;
        this.reservationService = reservationService;
        this.filmProjectionService = filmProjectionService;
    }

    @GetMapping("/projection/{id}/reservation")
    public ResponseEntity<ProjectionReservationDto> getReservationByProjection(@PathVariable long id){
        Optional<FilmProjection> projectionOpt = filmProjectionService.findFilmProjectionById(id);
        if (projectionOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        FilmProjection projection = projectionOpt.get();
        List<Reservation> reservations = cinemaService.findReservationByProjection(id);
        if(reservations != null) {
            ProjectionReservationDto projectionReservationDtos = new ProjectionReservationDto(reservations, projection);
            return ResponseEntity.ok(projectionReservationDtos);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest, @AuthenticationPrincipal User user) throws URISyntaxException {
        try {
            Reservation res = reservationService.createReservation(reservationRequest, user);
            URI location = new URI("/api/reservation/" + res.getId());
            ReservationDto created = new ReservationDto(res);
            return ResponseEntity.created(location).body(created);
        } catch (EntityNotFoundException | DuplicateReservationException | BusinessLogicExeception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
