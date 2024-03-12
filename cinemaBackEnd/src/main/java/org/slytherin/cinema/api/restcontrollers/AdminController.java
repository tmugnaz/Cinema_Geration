package org.slytherin.cinema.api.restcontrollers;

import org.apache.coyote.Response;
import org.slytherin.cinema.dtos.FilmDetailsDto;
import org.slytherin.cinema.dtos.FilmDto;
import org.slytherin.cinema.dtos.FilmProjectionDto;
import org.slytherin.cinema.dtos.UserDto;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.User;
import org.slytherin.cinema.model.exceptions.FilmNotFoundException;
import org.slytherin.cinema.model.exceptions.ProjectionNotFoundException;
import org.slytherin.cinema.model.exceptions.UnauthorizedOperationException;
import org.slytherin.cinema.model.services.abstraction.FilmProjectionService;
import org.slytherin.cinema.model.services.abstraction.FilmService;
import org.slytherin.cinema.model.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    FilmService filmService;
    FilmProjectionService filmProjectionService;
    UserService userService;

    @Autowired
    public AdminController(FilmService filmService,
                           FilmProjectionService filmProjectionService,
                           UserService userService) {
        this.filmService = filmService;
        this.filmProjectionService = filmProjectionService;
        this.userService = userService;
    }

    //    CRUD FILM
    @PostMapping("film/")
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('manager:create')")
    public ResponseEntity<FilmDetailsDto> createFilm(@RequestBody FilmDetailsDto filmDto,
                                                     @AuthenticationPrincipal User user) throws URISyntaxException {

        Film film = filmDto.fromDto();
        filmService.createFilm(film, user);
        URI location = new URI("/api/admin/film/" + film.getId());
        FilmDetailsDto created = new FilmDetailsDto(film);
        return ResponseEntity.created(location).body(created);


    }

    @DeleteMapping("film/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('manager:delete')")
    public ResponseEntity<?> deleteFilm(@PathVariable long id, @AuthenticationPrincipal User user) {
        try {
            filmService.deleteFilmById(id, user);
            return ResponseEntity.noContent().build();
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("film/{id}")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    public ResponseEntity<?> updateFilm(@PathVariable long id, @RequestBody Film updatedFilm, @AuthenticationPrincipal User user) {
        try {
            filmService.updateFilm(updatedFilm, user);
            return ResponseEntity.noContent().build();
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

//    CRUD PROJECTION

    @PostMapping("/projection/")
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('manager:create')")
    public ResponseEntity<FilmProjectionDto> createProjection(@RequestBody FilmProjectionDto projectionDto,
                                                              @AuthenticationPrincipal User user
                                                              ) throws URISyntaxException {
        try {

            Optional<Film> film = Optional.ofNullable(filmService.findFilmById(projectionDto.getFilmId()));
            if (film.isPresent()) {
                FilmProjection projection = projectionDto.fromDto(film.get());
                filmProjectionService.createProjection(projection, user);
                FilmProjectionDto result = new FilmProjectionDto(projection);
                URI location = new URI("/api/projection/" + result.getId());
                return ResponseEntity.created(location).body(result);
            }
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/projection")
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

    // CRUD USER
    @PostMapping("user/")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto,
                                                    @AuthenticationPrincipal User user) throws URISyntaxException {
        try {
            User newUser = userDto.fromDto();
            userService.createUser(newUser, user);
            UserDto result = new UserDto(newUser);
            URI location = new URI("/api/user/" + result.getId());
            return ResponseEntity.created(location).body(result);
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("user/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id, @AuthenticationPrincipal User user) {
        try {
            userService.deleteUserById(id, user);
            return ResponseEntity.noContent().build();
        } catch (ProjectionNotFoundException | UnauthorizedOperationException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("user/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Void> updateUser(@PathVariable long id,
                                           @RequestBody UserDto updatedUser,
                                           @AuthenticationPrincipal User user) {
        try {
            Optional<User> existingProjection = userService.findById(id);
            if (existingProjection.isPresent()) {
                updatedUser.setId(id);
                userService.updateUser(updatedUser.fromDto(), user);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.status(403).build();
        }
    }
}
