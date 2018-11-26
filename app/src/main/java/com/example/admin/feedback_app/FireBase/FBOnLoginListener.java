package com.example.admin.feedback_app.FireBase;

import com.google.firebase.auth.FirebaseUser;

public interface FBOnLoginListener {

    void onLogin(FirebaseUser user);

}
