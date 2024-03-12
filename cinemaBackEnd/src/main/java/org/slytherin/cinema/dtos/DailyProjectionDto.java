package org.slytherin.cinema.dtos;

import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.repositories.abstractions.FilmProjectionRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DailyProjectionDto {
    private long id;
    private String filmTitle;
    private String date;
    private String time;
    private int hallId;
    private String hallName;
    private String posterImage;
    private int duration;
    private Double averageRating;

    public DailyProjectionDto() {

    }

    public DailyProjectionDto(FilmProjection filmProjection) {
        this.id = filmProjection.getId();
        this.filmTitle = filmProjection.getFilmTitle();
        this.date = filmProjection.getProjectionDate().toString();
        this.time = filmProjection.getProjectionTimes().toString();
        this.hallId = (int) filmProjection.getHallId();
        this.hallName = filmProjection.getHallName();
        this.posterImage = filmProjection.getPosterImage();
        this.duration = filmProjection.getFilmDuration();
        this.averageRating = filmProjection.getFilm().calculateAverageRating();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
