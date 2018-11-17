package com.example.nia.moviestageone.db;

public class MovieDb {
    public static final String TABLE_NAME = "movies";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_USER_RATING = "userrating";
    public static final String COLUMN_RELEASE_DATE = "releasedate";
    public static final String COLUMN_POSTER_PATH = "posterpath";

    private int id;
    private String title;
    private String overview;
    private String userrating;
    private String releasedate;
    private String posterpath;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_OVERVIEW + " TEXT,"
                    + COLUMN_USER_RATING + " TEXT,"
                    + COLUMN_RELEASE_DATE + " TEXT,"
                    + COLUMN_POSTER_PATH + " TEXT"
                    + ")";

    public MovieDb() {
    }

    public MovieDb(int id,
             String title,
             String overview,
             String userrating,
             String releasedate,
             String posterpath) {

        this.id = id;
        this.title = title;
        this.overview = overview;
        this.userrating = userrating;
        this.releasedate = releasedate;
        this.posterpath = posterpath;
    }

}
