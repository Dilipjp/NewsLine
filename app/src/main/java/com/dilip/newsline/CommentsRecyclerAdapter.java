package com.dilip.newsline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CommentsRecyclerAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> comments;
    private DatabaseReference commentsDatabaseReference;

    public CommentsRecyclerAdapter(Context context, List<Comment> comments) {
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
        TextView tvComment = convertView.findViewById(R.id.tvComment);

        tvComment.setText(comment.getCommentText());


        return convertView;
    }
}
