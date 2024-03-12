package org.slytherin.cinema.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "performance")
public class Performance {
    @Id
    @GeneratedValue(generator = "performance_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "performance_id_generator", sequenceName = "performance_id_seq", allocationSize = 1)
    @Column(name = "performance_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;
    @Column(name="role_name")
    private String roleName;
    @Column(name="description")
    private String description;

    public Performance(long id, Film film, Actor actor, String roleName, String description) {
        this.id = id;
        this.film = film;
        this.actor = actor;
        this.roleName = roleName;
        this.description = description;
    }

    public Performance() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
