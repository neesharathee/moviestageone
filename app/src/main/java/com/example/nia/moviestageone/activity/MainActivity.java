package com.example.nia.moviestageone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.adapter.GalleryAdapter;
import com.example.nia.moviestageone.app.AppController;
import com.example.nia.moviestageone.db.DatabaseHelper;
import com.example.nia.moviestageone.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    StaggeredGridLayoutManager mLayoutManager;
    private  Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;
    private DatabaseHelper db;
    private List<Image> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);
        pDialog = new ProgressDialog(this);
        mLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        db = new DatabaseHelper(this);
        imageList.addAll(db.getAllMovies());


        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("title", images.get(position).getTitle());
                bundle.putInt("user_rating", images.get(position).getVoteAverage());
                bundle.putString("release_date", images.get(position).getDate());
                bundle.putString("overview", images.get(position).getOverview());
                bundle.putString("poster_path", images.get(position).getFinalURl());
                bundle.putInt("movie_id", images.get(position).getMovieId());

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        if (isOnline())
            fetchImages("popular");
        else Toast.makeText(this, "Check internet", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            String SearchItem = "popular";
            if(isOnline())
                fetchImages(SearchItem);
            else Toast.makeText(this,"Check internet",Toast.LENGTH_SHORT).show();

            mAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.row4) {
            String SearchItem = "top_rated";
            if(isOnline())
                fetchImages(SearchItem);
            else Toast.makeText(this,"Check internet",Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.row5) {
            mAdapter = new GalleryAdapter(getApplicationContext(), imageList);
            pDialog = new ProgressDialog(this);
            mLayoutManager = new StaggeredGridLayoutManager(2, 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            return true;
        }

        Log.d(TAG, "onOptionsItemSelected returned : returned");
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onPause() {
        super.onPause();
        pDialog.dismiss();
        mBundleRecyclerViewState = new Bundle();
        mListState =recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(getResources().getString(R.string.recycler_scroll_position_key), mListState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pDialog.dismiss();
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //When orientation is changed then grid column count is also changed so get every time
        Log.e(TAG, "onConfigurationChanged: " );
        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(getResources().getString(R.string.recycler_scroll_position_key));
                    recyclerView.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);
        }
        recyclerView.setLayoutManager(mLayoutManager);
    }

}

