package org.slytherin.cinema.model.repositories.abstractions;

import org.slytherin.cinema.model.entities.Feedback;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByUserId(long userId);
    List<Feedback> findByFilmId(long filmId);

}
