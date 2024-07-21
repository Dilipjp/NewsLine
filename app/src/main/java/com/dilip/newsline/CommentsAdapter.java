package com.dilip.newsline;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> comments;
    private DatabaseReference usersDatabaseReference;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
       // commentsDatabaseReference = FirebaseDatabase.getInstance().getReference("comments");
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

        tvComment.setText(comment.getCommentText());
        usersDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(comment.getCommentUserId());
        usersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("username").getValue(String.class);
                    tvUsername.setText(name);
                } else {
                    Log.d(TAG, "User data not found in the database");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user data", error.toException());
            }
        });
        return convertView;
    }
}
