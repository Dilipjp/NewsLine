package com.dilip.newsline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    Button register_btn;
    EditText editTextEmail, editTextPassword;
    TextView textViewErrorMessage,textViewSuccessMessage;
    private ProgressBar spinner;

    private FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner = findViewById(R.id.progressBar1);
        register_btn = findViewById(R.id.register_btn);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewErrorMessage = findViewById(R.id.textViewErrorMessage);
        textViewSuccessMessage = findViewById(R.id.textViewSuccessMessage);
        auth = FirebaseAuth.getInstance();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if(!email.isEmpty()){
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(!password.isEmpty()){
                            if(password.length() > 5){
                                auth.createUserWithEmailAndPassword(email,password)
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                FirebaseUser user = auth.getCurrentUser();
                                                if(user != null){
                                                    textViewSuccessMessage.setText(user.getEmail());
                                                }
                                                spinner.setVisibility(View.GONE);
                                                Toast.makeText(RegisterActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
                                                gotoHome();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                spinner.setVisibility(View.GONE);
                                                textViewErrorMessage.setText(e.getMessage().toString());
                                                Toast.makeText(RegisterActivity.this, "Sign up failed." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else {
                                editTextPassword.setError("Password should be more than six characters");
                            }
                        }else {
                            editTextPassword.setError("Password can't be empty");
                        }
                    }else {
                        editTextEmail.setError("Invalid Email");
                    }

                }else {
                    editTextEmail.setError("Email can't be empty");
                }
            }
        });
    }
    public void doSignIn(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class );
        startActivity(intent);
    }
    public void gotoHome(){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class );
        startActivity(intent);
        finish();
    }
}