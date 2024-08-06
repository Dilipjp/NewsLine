package com.dilip.newsline;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progressIndicator;
    Button btn_business,btn_entertainment,btn_general,btn_health,btn_science,btn_sports,btn_technology;
    SearchView search_bar;
    TextView textViewWelcomeText;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                profile();
                return true;
            case R.id.action_sign_out:
                signOut();
                return true;
            case R.id.action_settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("NewsLine");
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser user = auth.getCurrentUser();
        checkUser();

        textViewWelcomeText = findViewById(R.id.Tv_welcome);
        recyclerView = findViewById(R.id.news_recycler_view);
        progressIndicator = findViewById(R.id.progress_bar);
        btn_business = findViewById(R.id.btn_1);
        btn_entertainment = findViewById(R.id.btn_2);
        btn_general = findViewById(R.id.btn_3);
        btn_health = findViewById(R.id.btn_4);
        btn_science = findViewById(R.id.btn_5);
        btn_sports = findViewById(R.id.btn_6);
        btn_technology = findViewById(R.id.btn_7);
        btn_business.setOnClickListener(this);
        btn_entertainment.setOnClickListener(this);
        btn_general.setOnClickListener(this);
        btn_health.setOnClickListener(this);
        btn_science.setOnClickListener(this);
        btn_sports.setOnClickListener(this);
        btn_technology.setOnClickListener(this);
        search_bar = findViewById(R.id.search_bar);

        setupRecyclerView();
        getNews("general", null);



        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               // getNews("GENERAL", query);
                searchNews(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }





    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }

    void changeInProgress(boolean show) {
        if (show)
            progressIndicator.setVisibility(View.VISIBLE);
        else
            progressIndicator.setVisibility(View.INVISIBLE);
    }

    void getNews(String category, String query) {
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("9f5d4ef1ea5b44c9bf855efbe62b9fdb");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() -> {
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("error", throwable.getMessage());
                    }
                }
        );
    }
    void searchNews(String query) {
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("9f5d4ef1ea5b44c9bf855efbe62b9fdb");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .language("en")
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(() -> {
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("error", throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String category = btn.getText().toString();
        getNews(category, null);
    }

    public void settings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }
    public void signOut() {
        Intent intent = new Intent(MainActivity.this, SignOutActivity.class);
        startActivity(intent);
        finish();
    }
    public void profile() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void gotoLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkUser() {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Log.d(TAG, "user not sign");
                    gotoLogin();
                } else {
                    Log.d(TAG, "user signed: ");
                    // textViewWelcomeText.setText();
                    if (firebaseAuth.getCurrentUser() != null) {
                        String userId = firebaseAuth.getCurrentUser().getUid();
                        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    User user = snapshot.getValue(User.class);
                                    if (user != null) {
                                        String username = user.getUsername();
                                        textViewWelcomeText.setText("Welcome, " + username + "!");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d(TAG, "DatabaseError: " + error.getMessage());
                            }
                        });
                    }

                }
            }
        });
    }
}
