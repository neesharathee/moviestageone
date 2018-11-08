package com.example.nia.moviestageone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nia.moviestageone.R;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    Context context;
    int movieid;

    public TrailerAdapter(Context context, int movieid){

    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trailer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {
        String num= String.valueOf(position);
        holder.itemPosition.setText(num);
        holder.llTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        return 0;
    }
}
