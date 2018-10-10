package com.example.nia.moviestageone.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nia.moviestageone.R;

public class Main2Activity extends AppCompatActivity {
    TextView movieTitle, movieReleaseDate, movieUserRating, movieOverView;
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        movieTitle = findViewById(R.id.movie_title);
        movieReleaseDate = findViewById(R.id.release_date);
        movieUserRating = findViewById(R.id.vote_average);
        movieOverView = findViewById(R.id.movie_overview);
        moviePoster = findViewById(R.id.poster_image);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String title = bundle.getString("title");
        String overView = bundle.getString("overview");
        int rating = bundle.getInt("user_rating");
        String date = bundle.getString("release_date");
        String url = bundle.getString("poster_path");
        String ratingS = Integer.toString(rating);

        movieTitle.setText(title);
        movieReleaseDate.setText(date);
        movieUserRating.setText(ratingS);
        movieOverView.setText(overView);
        Glide.with(this).load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(moviePoster);

    }
}
