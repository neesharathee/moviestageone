package com.example.nia.moviestageone.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.adapter.TrailerAdapter;
import com.example.nia.moviestageone.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class TrailerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView emptyData;
    TrailerAdapter mAdapter;
    ArrayList<String> trailers;
    ArrayList<String> numbers;
    private ProgressDialog pDialog;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        trailers = new ArrayList<>();
        numbers = new ArrayList<>();
        pDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recycler);
        emptyData = findViewById(R.id.no_content);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int movieid = bundle.getInt("MOVIEID");

        mAdapter = new TrailerAdapter(this, trailers, numbers);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        fetchTrailers(movieid);

    }

    private void fetchTrailers(int movieid) {

        pDialog.setMessage("Hang on a sec...");
        pDialog.show();

        String BASE_URL = "https://api.themoviedb.org/3/movie/";
        String API_KEY_PARAMS = "?api_key=";
        String API_KEY = "7d2e7ba1548f223721f9fcdcc521fb6c";
        String LEFT_URL = "&language=en-US&append_to_response=videos";
        String endpoint = BASE_URL + movieid + API_KEY_PARAMS + API_KEY + LEFT_URL;

        //String endpoint = "https://api.themoviedb.org/3/movie/335983?api_key=7d2e7ba1548f223721f9fcdcc521fb6c&language=en-US&append_to_response=videos";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.hide();
                try {
                    int num = 1;
                    JSONObject jsonObject = response.getJSONObject("videos");
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject param = jsonArray.getJSONObject(i);
                        String videoId = param.getString("key");
                        trailers.add(videoId);
                        numbers.add(String.valueOf(num));
                        num++;
                    }
                    if(trailers.size()==0){
                        emptyData.setVisibility(View.VISIBLE);
                    }
                    else emptyData.setVisibility(View.GONE);

                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
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
