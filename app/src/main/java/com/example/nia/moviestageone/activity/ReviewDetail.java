package com.example.nia.moviestageone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nia.moviestageone.R;

public class ReviewDetail extends AppCompatActivity {
TextView tvContent,tvAuthor,tvUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        tvContent=findViewById(R.id.tv_content);
        tvAuthor=findViewById(R.id.tv_author);
        tvUrl=findViewById(R.id.tv_url);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String content = bundle.getString("content");
        String author = bundle.getString("author");
        String url = bundle.getString("url");

        tvContent.setText(content);
        tvAuthor.setText(author);
        tvUrl.setText(url);
    }
}
