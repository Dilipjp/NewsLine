package com.dilip.newsline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    private FirebaseAuth auth;
    private FirebaseUser user;
    private ImageView profileImage;
    private TextView profileName, profileEmail;
    private Button buttonGoback, buttonSignOut;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            Log.d(TAG, "No user signed in");
            Toast.makeText(this, "No user signed in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = user.getUid();
        Log.d(TAG, "User UID: " + uid);

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

        profileImage = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        buttonGoback = findViewById(R.id.button_go_back);
        buttonSignOut = findViewById(R.id.button_sign_out);

        fetchUserProfile();

        buttonGoback.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        buttonSignOut.setOnClickListener(view -> {
            auth.signOut();
            Intent intent = new Intent(ProfileActivity.this, SignOutActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void fetchUserProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    profileName.setText(name);
                    profileEmail.setText(email);
                } else {
                    Log.d(TAG, "User data not found in the database");
                    Toast.makeText(ProfileActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Failed to read user data", error.toException());
                Toast.makeText(ProfileActivity.this, "Failed to read user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
