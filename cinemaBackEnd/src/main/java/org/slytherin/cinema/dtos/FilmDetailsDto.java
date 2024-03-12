package org.slytherin.cinema.dtos;

import jakarta.persistence.Column;
import org.slytherin.cinema.model.entities.Actor;
import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.Performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilmDetailsDto {
    private Long id;
    private String title;
    private int duration;
    private String posterImage;
    private String director;
    private int releaseYear;
    private String description;
    private List<ActorDto> actorDto;


    public FilmDetailsDto() {
    }

    public FilmDetailsDto(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.director = film.getDirector();
        if (film.getReleaseYear() == null) {
            this.releaseYear = 0;
        } else {
            this.releaseYear = film.getReleaseYear();
        }

        this.posterImage = film.getPosterImg();
        this.description = film.getDescription();
        this.duration = film.getDuration();
        if (film.getPerformances() == null) {
            this.actorDto = new ArrayList<>();
        } else {
            this.actorDto = film.getPerformances().stream().map(ActorDto::new).toList();
        }
    }

    public Film fromDto(){

        return new Film(this.title,
                this.director, this.releaseYear,
                this.posterImage, this.duration, this.description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActorDto> getActorDto() {
        return actorDto;
    }

    public void setActorDto(List<ActorDto> actorDto) {
        this.actorDto = actorDto;
    }
}
