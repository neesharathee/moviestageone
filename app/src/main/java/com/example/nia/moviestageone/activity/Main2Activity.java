package com.example.nia.moviestageone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.nia.moviestageone.db.DatabaseHelper;
import com.example.nia.moviestageone.model.Image;
import com.example.nia.moviestageone.model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class Main2Activity extends AppCompatActivity {
    TextView movieTitle, movieReleaseDate, movieUserRating, movieOverView, movieTrailers, movieReviews,movieFav;
    ImageView moviePoster;
    RadioButton radioButton;
    private DatabaseHelper db;
    private List<Image> imageList = new ArrayList<>();

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
        movieFav = findViewById(R.id.tv_favorite);
        radioButton = findViewById(R.id.btn_fav);

        db = new DatabaseHelper(this);
        imageList.addAll(db.getAllMovies());
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String title = bundle.getString("title");
        final String overView = bundle.getString("overview");
        int rating = bundle.getInt("user_rating");
        final int movieid = bundle.getInt("movie_id");
        final String date = bundle.getString("release_date");
        final String url = bundle.getString("poster_path");
        final String ratingS = Integer.toString(rating);

        final Image image=new Image();
        image.setTitle(title);
        image.setOverview(overView);
        image.setVoteAverage(rating);
        image.setMovieId(movieid);
        image.setDate(date);
        image.setFinalURl(url);

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    createMovie(movieid,title,overView,ratingS,date,url);
                else db.deleteFact(image);
            }
        });
//        movieFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //save movie
//                createMovie(movieid,title,overView,ratingS,date,url);
//            }
//        });

        movieTrailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main2Activity.this, TrailerActivity.class);
                intent.putExtra("MOVIEID", movieid);
                startActivity(intent);

            }
        });
        movieReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main2Activity.this, ReviewsActivity.class);
                intent.putExtra("MOVIEID", movieid);
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
    private void createMovie(int id,
                             String title,
                             String overview,
                             String userrating,
                             String releasedate,
                             String posterpath) {
        // inserting note in db and getting
        // newly inserted note id
        long id1 = db.insertMovie(id,title,overview,userrating,releasedate,posterpath);

    Toast.makeText(this,"Movie added to favorites  "+id1, Toast.LENGTH_SHORT).show();
        // get the newly inserted note from db
//        Note n = db.getNote(id);
//
//        if (n != null) {
//            // adding new note to array list at 0 position
//            notesList.add(0, n);
//
//            // refreshing the list
//            mAdapter.notifyDataSetChanged();
//
//            toggleEmptyNotes();

    }
}
