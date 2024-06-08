package com.dilip.newsline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "your not sign", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);


        setupRecyclerView();
        getNews("general");
    }

    void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show){
        if(show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews(String category){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("9f5d4ef1ea5b44c9bf855efbe62b9fdb");
        newsApiClient.getTopHeadlines(
               new TopHeadlinesRequest.Builder()
                       .language("en")
                       .category(category)
                       .build(),
               new NewsApiClient.ArticlesResponseCallback() {
                   @Override
                   public void onSuccess(ArticleResponse response) {
                       runOnUiThread(()->{
                           changeInProgress(false);
                           articleList = response.getArticles();
                           adapter.updateData(articleList);
                           adapter.notifyDataSetChanged();
                       });
//                       response.getArticles().forEach((a) ->{
//                           Log.i("article",a.getUrl());
//                       });

                   }

                   @Override
                   public void onFailure(Throwable throwable) {
                       Log.i("error",throwable.getMessage());

                   }
               }
       );
    }


    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String category = btn.getText().toString();
        getNews(category);
    }
}