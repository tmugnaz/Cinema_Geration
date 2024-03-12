package org.slytherin.cinema.model.repositories.abstractions;

import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findAllByFilmId(long filmId);
}
