package com.example.nia.moviestageone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.adapter.ReviewsAdapter;
import com.example.nia.moviestageone.adapter.TrailerAdapter;
import com.example.nia.moviestageone.app.AppController;
import com.example.nia.moviestageone.model.Image;
import com.example.nia.moviestageone.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class Main2Activity extends AppCompatActivity {
    TextView movieTitle, movieReleaseDate, movieUserRating, movieOverView,movieTrailers,movieReviews;
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
        movieTrailers = findViewById(R.id.movie_trailers);
        movieReviews = findViewById(R.id.movie_reviews);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String title = bundle.getString("title");
        String overView = bundle.getString("overview");
        int rating = bundle.getInt("user_rating");
        final int movieid = bundle.getInt("movie_id");
        String date = bundle.getString("release_date");
        String url = bundle.getString("poster_path");
        String ratingS = Integer.toString(rating);

movieTrailers.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(Main2Activity.this,TrailerActivity.class);
        intent.putExtra("MOVIEID",movieid);
        startActivity(intent);

    }
});
movieReviews.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(Main2Activity.this,ReviewsActivity.class);
        intent.putExtra("MOVIEID",movieid);
        startActivity(intent);
    }
});

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
