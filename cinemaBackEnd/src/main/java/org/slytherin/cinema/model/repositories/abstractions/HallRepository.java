package org.slytherin.cinema.model.repositories.abstractions;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {
    @Query("""
            SELECT p.hall FROM FilmProjection p WHERE p.id =:id
            """)
    Hall findByProjectionId(long id);

}
