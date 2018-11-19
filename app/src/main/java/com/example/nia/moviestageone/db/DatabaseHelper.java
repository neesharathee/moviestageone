package com.example.nia.moviestageone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import com.example.nia.moviestageone.model.Image;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movies_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(MovieDb.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MovieDb.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertMovie(int id,
                            String title,
                            String overview,
                            String userrating,
                            String releasedate,
                            String posterpath) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(MovieDb.COLUMN_ID, id);
        values.put(MovieDb.COLUMN_TITLE, title);
        values.put(MovieDb.COLUMN_OVERVIEW, overview);
        values.put(MovieDb.COLUMN_USER_RATING, userrating);
        values.put(MovieDb.COLUMN_RELEASE_DATE, releasedate);
        values.put(MovieDb.COLUMN_POSTER_PATH, posterpath);

        // insert row
        long id1 = db.insert(MovieDb.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id1;
    }
    public List<Image> getAllMovies() {
        List<Image> imagesList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MovieDb.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image image = new Image();
                image.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieDb.COLUMN_ID)));
                image.setTitle(cursor.getString(cursor.getColumnIndex(MovieDb.COLUMN_TITLE)));
                image.setOverview(cursor.getString(cursor.getColumnIndex(MovieDb.COLUMN_OVERVIEW)));
                image.setVoteAverage(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieDb.COLUMN_USER_RATING))));
                image.setDate(cursor.getString(cursor.getColumnIndex(MovieDb.COLUMN_RELEASE_DATE)));
                image.setFinalURl(cursor.getString(cursor.getColumnIndex(MovieDb.COLUMN_POSTER_PATH)));
                imagesList.add(image);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return imagesList;
    }
    public int getFactsCount() {
        String countQuery = "SELECT  * FROM " + MovieDb.TABLE_NAME;
      SQLiteDatabase db = this.getReadableDatabase();
       // SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        //DatabaseManager.getInstance().closeDatabase();
        // return count
        return count;
    }

    public void deleteFact(Image image) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MovieDb.TABLE_NAME, MovieDb.COLUMN_ID + " = ?",
                new String[]{String.valueOf(image.getMovieId())});
      db.close();
      //  DatabaseManager.getInstance().closeDatabase();
    }
    public void deleteAllFact() {
       SQLiteDatabase db = this.getWritableDatabase();
       // SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(MovieDb.TABLE_NAME, null,
                null);
      db.close();

       // DatabaseManager.getInstance().closeDatabase();
    }
}