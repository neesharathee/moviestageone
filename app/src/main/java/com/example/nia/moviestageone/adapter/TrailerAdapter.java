package com.example.nia.moviestageone.adapter;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.app.AppController;
import com.example.nia.moviestageone.model.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    Context context;
    String videoId;
    ArrayList<String> trailers;
    ArrayList<String> numbers;



    public TrailerAdapter(Context context,  ArrayList<String> trailers,ArrayList<String> numbers){
        this.context=context;
        this.trailers=trailers;
        this.numbers=numbers;

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trailer, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {
        final String youtubeVidId=trailers.get(position);
        String num= numbers.get(position);
        holder.itemPosition.setText(num);
        holder.llTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://"+youtubeVidId)));
                //watchYoutubeVideo(context,youtubeVidId);
               // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youtubeVidId));
                //context.startActivity(intent);
                playVideo(youtubeVidId);
               // URL url = "https://www.youtube.com/watch?v="+youtubeVidId;
            }
        });

    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemPosition;
        LinearLayout llTrailer;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemPosition = itemView.findViewById(R.id.item_position);
            llTrailer = itemView.findViewById(R.id.ll_trailer);
        }
    }

    @Override
    public int getItemCount() {
//if(trailers.size()==0)

        return trailers.size();
    }

    public  void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
    public void playVideo(String key){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
        }

        context.startActivity(intent);
    }
    public void playMedia(Uri file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(file);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
