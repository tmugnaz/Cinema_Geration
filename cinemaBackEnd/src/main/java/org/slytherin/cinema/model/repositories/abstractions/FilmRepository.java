package org.slytherin.cinema.model.repositories.abstractions;

import org.slytherin.cinema.model.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Film findById(long filmId);
    List<Film> findByGenresGenreName(String genreName);
    @Query("""
            SELECT f FROM Film f JOIN f.feedbackList fb 
            GROUP BY f ORDER BY AVG(fb.rating) DESC LIMIT 10
                """)
    List<Film> findTop10ByAverage();


}
