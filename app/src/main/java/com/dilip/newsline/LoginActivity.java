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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    EditText editTextEmail, editTextPassword;
    TextView textViewErrorMessage,textViewSuccessMessage;
    private ProgressBar spinner;

    private FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.progressBar1);
        login_btn = findViewById(R.id.login_btn);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewErrorMessage = findViewById(R.id.textViewErrorMessage);
        textViewSuccessMessage = findViewById(R.id.textViewSuccessMessage);
        auth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if(!email.isEmpty()){
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(!password.isEmpty()){
                            if(password.length() > 5){
                                spinner.setVisibility(View.VISIBLE);
                                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                FirebaseUser user = auth.getCurrentUser();
                                                if(user != null){
                                                    textViewSuccessMessage.setText(user.getEmail());
                                                }
                                                spinner.setVisibility(View.GONE);
                                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                                                gotoHome();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                spinner.setVisibility(View.GONE);
                                                String errorMessage = "Authentication failed: " + e.getLocalizedMessage();
                                                textViewErrorMessage.setText(errorMessage);
                                                Toast.makeText(LoginActivity.this, errorMessage + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

    public void doSignUp(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class );
        startActivity(intent);
    }
    public void gotoHome(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class );
        startActivity(intent);
        finish();
    }
}