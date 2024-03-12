package org.slytherin.cinema.model.repositories.abstractions;

import org.slytherin.cinema.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
