package com.dilip.newsline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_profile);

        auth = FirebaseAuth.getInstance();

        Button yesButton = findViewById(R.id.buttonYes);
        Button noButton = findViewById(R.id.buttonNo);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void deleteProfile() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(DeleteProfileActivity.this, "Profile deleted successfully", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                            } else {
                                Toast.makeText(DeleteProfileActivity.this, "Failed to delete profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_CANCELED);
                            }
                            finish();
                        }
                    });
        } else {
            Toast.makeText(this, "No user signed in", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
