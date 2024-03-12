package org.slytherin.cinema.api.restcontrollers;

import org.slytherin.cinema.dtos.*;
import org.slytherin.cinema.model.entities.*;
import org.slytherin.cinema.model.services.abstraction.CinemaService;
import org.slytherin.cinema.model.services.abstraction.FilmService;
import org.slytherin.cinema.model.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    UserService userService;
    CinemaService cinemaService;
    FilmService filmservice;

    @Autowired
    public UserController(UserService userService, CinemaService cinemaService,FilmService filmservice) {
        this.filmservice= filmservice;
        this.userService = userService;
        this.cinemaService = cinemaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable long id, @AuthenticationPrincipal User user) {
        Optional<User> os = userService.findById(id);
        if (os.isPresent()) {
            UserDto userDto = new UserDto(os.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<User> users=  userService.findAllUser();
        List<UserDto> result = users.stream().map(UserDto :: new ).toList();

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{userId}/feedback")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByUserId(@PathVariable Long userId) {
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User u = userOpt.get();

        List<Feedback> feedbacks = cinemaService.findFeedbackByUser(userId);
        if (feedbacks != null) {
            List<FeedbackDto> feedbackDto = feedbacks.stream().map(FeedbackDto::new).toList();
            return ResponseEntity.ok(feedbackDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{filmId}/feedback")

    public ResponseEntity<FeedbackDto> createFeedback(@RequestBody FeedbackDto feedback, @AuthenticationPrincipal User user, @PathVariable long filmId) throws URISyntaxException {
        Film film = filmservice.findFilmById(filmId);

        Optional<User> u = userService.findById(user.getId());
        if(u.isPresent()){
            Feedback fd = feedback.fromDto(film,u.get());
            cinemaService.createFeedback(fd,user);
            URI location = new URI("/api/user/feedback/" + fd.getId());
            FeedbackDto created = new FeedbackDto(fd);
            return ResponseEntity.created(location).body(created);}
        else {
            return ResponseEntity.badRequest().build();}



    }

    @GetMapping("/{userId}/reservation")
    public ResponseEntity<List<ReservationDto>> getReservationByUserId(@PathVariable Long userId) {
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User u = userOpt.get();

        List<Reservation> reservations = cinemaService.findReservationByUser(userId);
        if (reservations != null) {
            List<ReservationDto> reservationDtos = reservations.stream().map(ReservationDto::new).toList();
            return ResponseEntity.ok(reservationDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
