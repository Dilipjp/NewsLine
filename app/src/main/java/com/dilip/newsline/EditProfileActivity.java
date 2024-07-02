package com.dilip.newsline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    private EditText editTextUsername;
    private Button buttonSave;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfileChanges();
            }
        });

        fetchUserProfile();
    }

    private void fetchUserProfile() {
        databaseReference.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                String username = snapshot.child("username").getValue(String.class);
                editTextUsername.setText(username);
            } else {
                Log.d(TAG, "User data not found");
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to fetch user profile", e);
            Toast.makeText(this, "Failed to fetch user profile", Toast.LENGTH_SHORT).show();
        });
    }

    private void saveProfileChanges() {
        String newUsername = editTextUsername.getText().toString().trim();

        if (TextUtils.isEmpty(newUsername)) {
            editTextUsername.setError("Username cannot be empty");
            editTextUsername.requestFocus();
            return;
        }


        databaseReference.child("username").setValue(newUsername)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditProfileActivity.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to update username", e);
                    Toast.makeText(EditProfileActivity.this, "Failed to update username", Toast.LENGTH_SHORT).show();
                });
    }
}
