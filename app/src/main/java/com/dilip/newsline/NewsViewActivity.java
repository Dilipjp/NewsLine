package com.dilip.newsline;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class NewsViewActivity extends AppCompatActivity {

    private TextView titleTextView, sourceTextView, authorTextView, descriptionTextView, dateTextView, contentTextView;
    private ImageView imageView;
    private Button button_submit_comment;
    private EditText editTextComment;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        auth = FirebaseAuth.getInstance();

        titleTextView = findViewById(R.id.article_title);
        sourceTextView = findViewById(R.id.article_source);
        authorTextView = findViewById(R.id.article_author);
        descriptionTextView = findViewById(R.id.article_description);
        dateTextView = findViewById(R.id.article_publishedAt);
        contentTextView = findViewById(R.id.article_content);
        imageView = findViewById(R.id.article_image);

        //submit comment section
        button_submit_comment = findViewById(R.id.button_submit_comment);
        editTextComment = findViewById(R.id.editTextComment);

        button_submit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editTextComment.getText().toString().trim();
                String userId = auth.getCurrentUser().getUid();
                String title = getIntent().getStringExtra("title");
                if(!TextUtils.isEmpty(comment)){
                    saveComment(comment, userId, title);
                }else {
                    editTextComment.setError("Comment can't be empty");
                    editTextComment.requestFocus();
                }
            }
        });

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
    private void saveComment(String comment, String userId, String title){
        // save comments in db
    }
}
