package org.slytherin.cinema.model.repositories.abstractions;

import org.slytherin.cinema.model.entities.Feedback;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FilmProjectionRepository extends JpaRepository<FilmProjection,Long> {
    List<FilmProjection>findByProjectionDate(LocalDate projectionDate);
    List<FilmProjection> findByProjectionDateBetween(LocalDate startDate, LocalDate endDate);
    List<FilmProjection> findByFilmIdAndProjectionDate(Long filmId, LocalDate date);
    @Query("""
            SELECT p.hall FROM FilmProjection p WHERE p.id =:id
            """)
    List<FilmProjection> findByHall(long id);
}
