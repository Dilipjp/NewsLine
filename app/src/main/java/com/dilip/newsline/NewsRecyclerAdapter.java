package com.dilip.newsline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {
    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSource().getName());
        holder.dateTextView.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.no_image)
                .placeholder(R.drawable.no_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    void updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView, sourceTextView, dateTextView;
        ImageView imageView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            dateTextView = itemView.findViewById(R.id.article_publishedAt);
            imageView = itemView.findViewById(R.id.article_image);
        }
    }
}
