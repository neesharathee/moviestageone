package com.example.nia.moviestageone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.adapter.TrailerAdapter;
import com.example.nia.moviestageone.app.AppController;
import com.example.nia.moviestageone.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    TextView movieTitle, movieReleaseDate, movieUserRating, movieOverView,movieTrailers;
    RecyclerView recyclerView;
    private ProgressDialog pDialog;
    ImageView moviePoster;
    TrailerAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pDialog= new ProgressDialog(this);
        mAdapter= new TrailerAdapter(this);
        movieTitle = findViewById(R.id.movie_title);
        movieReleaseDate = findViewById(R.id.release_date);
        movieUserRating = findViewById(R.id.vote_average);
        movieOverView = findViewById(R.id.movie_overview);
        moviePoster = findViewById(R.id.poster_image);
        movieTrailers = findViewById(R.id.movie_trailers);
        recyclerView = findViewById(R.id.recycler);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String title = bundle.getString("title");
        String overView = bundle.getString("overview");
        int rating = bundle.getInt("user_rating");
        int movieid = bundle.getInt("movie_id");
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
    private void fetchImages(String SearchItem) {

        pDialog.setMessage("Hang on a sec...");
        pDialog.show();

        String flickrQuery_per_page = "&page=10";
        String flickrQuery_url = "https://api.themoviedb.org/3/movie/";
        String FlickrQuery_key = "?api_key=";
        String FlickrApiKey = "7d2e7ba1548f223721f9fcdcc521fb6c";
        String endpoint = " ";

        if (SearchItem.equalsIgnoreCase("popular")) {
            endpoint = flickrQuery_url + SearchItem + FlickrQuery_key + FlickrApiKey + flickrQuery_per_page;
        } else if (SearchItem.equalsIgnoreCase("top_rated")) {
            endpoint = flickrQuery_url + SearchItem + FlickrQuery_key + FlickrApiKey + flickrQuery_per_page;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.hide();
                images.clear();
                try {

                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject param = jsonArray.getJSONObject(i);
                        final Image image = new Image();
                        String title = param.getString("title");
                        String overview = param.getString("overview");
                        String date = param.getString("release_date");
                        int voteAverage = param.getInt("vote_average");
                        int movieId = param.getInt("id");
                        String posterImage = param.getString("poster_path");
                        String temp = posterImage.trim();
                        String URL = "http://image.tmdb.org/t/p/original/" + temp;
                        image.setTitle(title);
                        image.setOverview(overview);
                        image.setDate(date);
                        image.setVoteAverage(voteAverage);
                        image.setMovieId(movieId);
                        image.setFinalURl(URL);
                        images.add(image);


                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }


                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }

        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
