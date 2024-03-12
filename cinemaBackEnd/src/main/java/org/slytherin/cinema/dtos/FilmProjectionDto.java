package org.slytherin.cinema.dtos;

import org.slytherin.cinema.model.entities.Film;
import org.slytherin.cinema.model.entities.FilmProjection;
import org.slytherin.cinema.model.entities.Hall;
import org.slytherin.cinema.model.entities.PriceType;
import org.slytherin.cinema.model.repositories.abstractions.FilmProjectionRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FilmProjectionDto {
    private long id;
    private long filmId;
    private String filmTitle;
    private String date;
    private String time;
    private long hallId;
    private String hallName;
    private String posterImage;
    private int duration;
    private Double ticketPrice;
    public FilmProjectionDto() {

    }

    public FilmProjectionDto(FilmProjection filmProjection) {
        this.id = filmProjection.getId();
        this.filmId = filmProjection.getFilm().getId();
        this.filmTitle = filmProjection.getFilmTitle();
        this.date = filmProjection.getProjectionDate().toString();
        this.time = filmProjection.getProjectionTimes().toString();
        this.hallId =  filmProjection.getHallId();
        this.hallName = filmProjection.getHallName();
        this.posterImage = filmProjection.getPosterImage();
        this.duration = filmProjection.getFilmDuration();
        this.ticketPrice = filmProjection.getTicketPrice();

    }

    public FilmProjection fromDto(){

        Film film = new Film();
        film.setId(this.filmId);
        film.setTitle(this.filmTitle);
        film.setPosterImg(this.posterImage);
        film.setDuration(this.duration);
        Hall hall = new Hall();
        hall.setId(this.hallId);
        hall.setName(this.hallName);
        return new FilmProjection(this.id, film, hall,this.ticketPrice,this.date,this.time);
    }
    public FilmProjection fromDto(Film film){

        Hall hall = new Hall();
        hall.setId(this.hallId);
        hall.setName(this.hallName);
        return new FilmProjection(this.id, film, hall,this.ticketPrice,this.date,this.time);
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

    public long getHallId() {
        return hallId;
    }

    public void setHallId(long hallId) {
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

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public long getFilmId() {
        return filmId;
    }

    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }
}
