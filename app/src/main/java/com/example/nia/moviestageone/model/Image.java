package com.example.nia.moviestageone.model;

import java.io.Serializable;

public class Image  implements Serializable {

    private String title;
    private String overview,date;
    private int voteAverage, movieId;
    private String posterImage;
    private String FinalURl;

    public String getFinalURl() {
        return FinalURl;
    }

    public void setFinalURl(String finalURl) {
        FinalURl = finalURl;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }




}