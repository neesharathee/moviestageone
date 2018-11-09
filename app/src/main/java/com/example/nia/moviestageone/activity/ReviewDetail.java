package com.example.nia.moviestageone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nia.moviestageone.R;

public class ReviewDetail extends AppCompatActivity {
TextView tvContent,tvAuthor,tvUrl;
String content,author,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        tvContent=findViewById(R.id.tv_content);
        tvAuthor=findViewById(R.id.tv_author);
        tvUrl=findViewById(R.id.tv_url);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
             content = bundle.getString("content");
             author = bundle.getString("author");
             url = bundle.getString("url");
        } else
        {
             content = "No content found";
             author = "No content found";
             url = "No content found";
        }

        tvContent.setText(content);
        tvAuthor.setText(author);
        tvUrl.setText(url);
    }
}
