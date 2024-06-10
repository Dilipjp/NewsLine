package com.dilip.newsline;

import static android.content.ContentValues.TAG;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

public class UserAuthState {
    private FirebaseAuth auth;
    private Boolean isUserSignedIn;
    public UserAuthState(Boolean isSign) {
        isUserSignedIn = isSign;
        auth = FirebaseAuth.getInstance();
    }


    public Boolean getUserSignedIn() {
        return isUserSignedIn;
    }

    public void setUserSignedIn(Boolean userSignedIn) {
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    isUserSignedIn = false;
                }else {
                    isUserSignedIn = true;
                }
            }
        });
        isUserSignedIn = userSignedIn;
    }

}
