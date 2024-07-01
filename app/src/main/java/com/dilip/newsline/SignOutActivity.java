package com.dilip.newsline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        Button btnSignOut = findViewById(R.id.btn_sign_out);
        Button btnCancel = findViewById(R.id.btn_cancel);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutAndRedirect();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSignOut();
            }
        });
    }

    private void signOutAndRedirect() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(SignOutActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void cancelSignOut() {
        Intent intent = new Intent(SignOutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
