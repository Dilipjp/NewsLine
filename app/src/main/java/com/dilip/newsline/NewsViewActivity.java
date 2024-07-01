package com.dilip.newsline;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class NewsViewActivity extends AppCompatActivity {

    private TextView titleTextView, sourceTextView, authorTextView, descriptionTextView, dateTextView, contentTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        titleTextView = findViewById(R.id.article_title);
        sourceTextView = findViewById(R.id.article_source);
        authorTextView = findViewById(R.id.article_author);
        descriptionTextView = findViewById(R.id.article_description);
        dateTextView = findViewById(R.id.article_publishedAt);
        contentTextView = findViewById(R.id.article_content);
        imageView = findViewById(R.id.article_image);

        // Get data from intent
        String title = getIntent().getStringExtra("title");
        String source = getIntent().getStringExtra("source");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("publishedAt");
        String content = getIntent().getStringExtra("content");
        String imageUrl = getIntent().getStringExtra("urlToImage");

        // Set data to views
        titleTextView.setText(title);
        sourceTextView.setText(source);
        authorTextView.setText(author);
        descriptionTextView.setText(description);
        dateTextView.setText(date);
        contentTextView.setText(content);
        Picasso.get().load(imageUrl)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(imageView);
    }
}
