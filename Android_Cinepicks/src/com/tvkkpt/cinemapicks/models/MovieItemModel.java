package com.tvkkpt.cinemapicks.models;

public class MovieItemModel {

    private String movieId;
    private String movieNameVar;
    private String movieTitle;
    private String movieShortTitle;
    private String movieDescription;
    private String director;
    private String casting;
    private String duration;
    private String genre;
    private String releaseDate;
    private String thumbUrl;
    private String videoUrl;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieNameVar() {
        return movieNameVar;
    }

    public void setMovieNameVar(String _movieNameVar) {
        this.movieNameVar = _movieNameVar;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String _movieTitle) {
        this.movieTitle = _movieTitle;
    }

    public String getMovieShortTitle() {
        return movieShortTitle;
    }

    public void setMovieShortTitle(String _movieShortTitle) {
        this.movieShortTitle = _movieShortTitle;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String _movieDescription) {
        this.movieDescription = _movieDescription;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String _director) {
        this.director = _director;
    }

    public String getCasting() {
        return casting;
    }

    public void setCasting(String _casting) {
        this.casting = _casting;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
