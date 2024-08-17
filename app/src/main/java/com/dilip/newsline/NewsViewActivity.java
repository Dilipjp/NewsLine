package com.dilip.newsline;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NewsViewActivity extends AppCompatActivity {

    private TextView titleTextView, sourceTextView, authorTextView, descriptionTextView, dateTextView, contentTextView;
    private ImageView imageView;
    private Button buttonSubmitComment, buttonShare;
    private EditText editTextComment;
    private FirebaseAuth auth;
    private DatabaseReference commentsDatabaseReference;
    private List<Comment> comments;
    private CommentsAdapter adapter;

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

        // Share button
        buttonShare = findViewById(R.id.button_share);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareNewsArticle();
            }
        });

        // Comment section
        String articleTitle = getIntent().getStringExtra("title");
        commentsDatabaseReference = FirebaseDatabase.getInstance().getReference("comments").child(createSlug(articleTitle));

        buttonSubmitComment = findViewById(R.id.button_submit_comment);
        editTextComment = findViewById(R.id.editTextComment);

        buttonSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentText = editTextComment.getText().toString().trim();
                String commentUserId = auth.getCurrentUser().getUid();
                if (!TextUtils.isEmpty(commentText)) {
                    saveComment(articleTitle, commentText, commentUserId);
                } else {
                    editTextComment.setError("Comment can't be empty");
                    editTextComment.requestFocus();
                }
            }
        });

        // Get data from intent
        String source = getIntent().getStringExtra("source");
        String author = getIntent().getStringExtra("author");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("publishedAt");
        String content = getIntent().getStringExtra("content");
        if (content != null && content.length() > 200) {
            content = content.substring(0, 200) + "...";
        }
        String imageUrl = getIntent().getStringExtra("urlToImage");

        // Set data to views
        titleTextView.setText(articleTitle);
        sourceTextView.setText(source);
        authorTextView.setText(author);
        descriptionTextView.setText(description);
        dateTextView.setText(formatDate(date));
        contentTextView.setText(content);
        Picasso.get().load(imageUrl)
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(imageView);

        // Load comments
        loadComments();
    }

    private String createSlug(String title) {
        String slug = title.toLowerCase();
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("[^a-z0-9-]", "");
        return slug;
    }

    private String formatDate(String date) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String outputPattern = "MMM dd, yyyy HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date parsedDate;
        String readableDate = null;
        try {
            parsedDate = inputFormat.parse(date);
            readableDate = outputFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return readableDate;
    }

    private void saveComment(String commentTitle, String commentText, String commentUserId) {
        String commentId = commentsDatabaseReference.push().getKey();
        Comment comment = new Comment(commentTitle, commentText, commentUserId);
        if (commentId != null) {
            commentsDatabaseReference.child(commentId).setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(NewsViewActivity.this, "Comment successfully added", Toast.LENGTH_SHORT).show();
                    editTextComment.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(NewsViewActivity.this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                    editTextComment.setText("");
                }
            });
        }
    }

    private void loadComments() {
        ListView listView = findViewById(R.id.listView);
        comments = new ArrayList<>();
        adapter = new CommentsAdapter(this, comments);
        listView.setAdapter(adapter);

        commentsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Comment comment = snapshot.getValue(Comment.class);
                        if (comment != null) {
                            comment.setCommentId(snapshot.getKey());
                            comments.add(comment);
                        }
                    }
                }
                Collections.reverse(comments);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("NewsViewActivity", "Database error: " + error.getMessage());
            }
        });
    }

    private void shareNewsArticle() {
        String shareTitle = getIntent().getStringExtra("title");
        String shareContent = getIntent().getStringExtra("content");

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this news article");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareTitle + "\n\n" + shareContent);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
