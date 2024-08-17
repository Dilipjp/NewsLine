package com.dilip.newsline;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> comments;
    private DatabaseReference usersDatabaseReference, commentsDatabaseReference;
    private FirebaseUser currentUser;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        }

        final Comment comment = comments.get(position);
        TextView tvUsername = convertView.findViewById(R.id.tvUserName);
        TextView tvComment = convertView.findViewById(R.id.tvComment);
        ImageButton btnEdit = convertView.findViewById(R.id.btnEditComment);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDeleteComment);

        tvComment.setText(comment.getCommentText());

        // Fetch and display the username
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(comment.getCommentUserId());
        usersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("username").getValue(String.class);
                    tvUsername.setText(name);

                    // Show edit/delete buttons only if the current user is the author
                    if (currentUser != null && currentUser.getUid().equals(comment.getCommentUserId())) {
                        btnEdit.setVisibility(View.VISIBLE);
                        btnDelete.setVisibility(View.VISIBLE);
                    } else {
                        btnEdit.setVisibility(View.GONE);
                        btnDelete.setVisibility(View.GONE);
                    }
                } else {
                    Log.d(TAG, "User data not found in the database");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user data", error.toException());
            }
        });

        // Edit comment
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditCommentDialog(comment);
            }
        });

        // Delete comment
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComment(comment, comment.commentTitle);
            }
        });

        return convertView;
    }

    private void showEditCommentDialog(Comment comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Comment");

        final EditText input = new EditText(context);
        input.setText(comment.getCommentText());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newCommentText = input.getText().toString().trim();
            if (!newCommentText.isEmpty()) {
                updateComment(comment.getCommentId(), newCommentText, comment.commentTitle);
            } else {
                Toast.makeText(context, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateComment(String commentId, String newCommentText, String commentTitle) {
        commentsDatabaseReference = FirebaseDatabase.getInstance().getReference("comments").child(createSlug(commentTitle)).child(commentId);
        commentsDatabaseReference.child("commentText").setValue(newCommentText)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Comment updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to update comment", Toast.LENGTH_SHORT).show());
    }

    private void deleteComment(Comment comment, String commentTitle) {
        commentsDatabaseReference = FirebaseDatabase.getInstance().getReference("comments").child(createSlug(commentTitle)).child(comment.getCommentId());
        commentsDatabaseReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    comments.remove(comment);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Comment deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete comment", Toast.LENGTH_SHORT).show());
    }
    private String createSlug(String title) {
        String slug = title.toLowerCase();
        slug = slug.replaceAll("\\s+", "-");
        slug = slug.replaceAll("[^a-z0-9-]", "");
        return slug;
    }
}
