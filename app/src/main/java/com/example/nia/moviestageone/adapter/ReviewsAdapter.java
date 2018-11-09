package com.example.nia.moviestageone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nia.moviestageone.R;
import com.example.nia.moviestageone.activity.ReviewDetail;
import com.example.nia.moviestageone.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Review> reviewArrayList;
    ArrayList<String> numbers;


    public ReviewsAdapter(Context context, ArrayList<Review> reviewArrayList, ArrayList<String> numbers) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
        this.numbers = numbers;

    }

    @Override
    public ReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.MyViewHolder holder, final int position) {


        String num = numbers.get(position);
        holder.itemPosition.setText(num);
        holder.llTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Review review = reviewArrayList.get(position);
                 String content = review.getContent();
                 String author = review.getAuthor();
                 String url = review.getUrl();

                Bundle bundle = new Bundle();
                bundle.putString("content", content);
                bundle.putString("author", author);
                bundle.putString("url", url);
                Intent intent = new Intent(context, ReviewDetail.class);
                intent.putExtra("Review_bundle", bundle);
                context.startActivity(intent);

            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
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


        return reviewArrayList.size();
    }


}
