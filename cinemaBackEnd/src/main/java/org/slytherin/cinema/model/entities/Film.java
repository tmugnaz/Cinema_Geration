package org.slytherin.cinema.model.entities;
import jakarta.persistence.*;
import org.slytherin.cinema.dtos.ActorDto;

import java.util.Date;
import java.util.List;
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(generator = "film_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "film_id_generator", sequenceName = "film_id_seq", allocationSize = 1)
    @Column(name = "film_id")
    private long id;
    private String title;
    private String director;
    @Column(name = "release_year")
    private Integer releaseYear;
    @Column(name = "poster_img")
    private String posterImg;
    @Column(name = "description")
    private String description;
    @Column(name="release_date")
    private Date releaseDate;
    @Column(name="is_available")
    private Boolean isAvailable;
    @Column(name="end_date")
    private Date endDate;
    @Column( name = "tagline")
    private String tagline;
    @Column(name = "duration")
    private Integer duration;
    @OneToMany(mappedBy = "film",fetch = FetchType.EAGER)
    private List<Performance> performances;
    @ManyToMany
    @JoinTable(name = "film_genre", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;
    @OneToMany(mappedBy = "film")
    private List<FilmProjection> projections;
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Feedback> feedbackList;

    public Film() {
    }

    public Film(long id, String title, String director, Integer releaseYear, String posterImg, String description,
                String tagline, Integer duration,List<Genre> genres, List<FilmProjection> projections,
                List<Feedback> feedbackList, Date releaseDate) {

        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.posterImg = posterImg;
        this.description = description;
        this.tagline= tagline;
        this.duration = duration;
        this.genres = genres;
        this.projections = projections;
        this.feedbackList = feedbackList;
        this.releaseDate= releaseDate;
    }

    public Film(long id, String title,
                String director, Integer releaseYear,
                String posterImg, Integer duration, String description) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.posterImg = posterImg;
        this.duration = duration;
        this.description = description;

    }

    public Film(String title, String director, int releaseYear, String posterImage, int duration, String description) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.posterImg = posterImage;
        this.duration = duration;
        this.description = description;
    }


    public Double calculateAverageRating() {
        if (feedbackList == null || feedbackList.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        for (Feedback feedback : feedbackList) {
            totalRating += feedback.getRating();
        }

        return (double) totalRating / feedbackList.size();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTagline() {return tagline; }

    public void setTagline(String tagline) {this.tagline = tagline; }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    public List<FilmProjection> getProjections() {
        return projections;
    }

    public void setProjections(List<FilmProjection> projections) {
        this.projections = projections;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }
}
