package com.example.admin.feedback_app.FireBase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.feedback_app.mødeholder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseLogik {

    private final String TAG = "FirebaseLogik";
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private FBOnUserCreatedListener userCreatedListener;
    private FBOnLoginListener loginListener;

    public FirebaseLogik(){
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void indsætMødeholderData(final mødeholder mødeholder, final Activity akt) {

        mAuth.createUserWithEmailAndPassword(mødeholder.getEmail(),mødeholder.getPassword()).addOnCompleteListener(akt, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //sætter data over på firebase
                    mFirestore.collection("mødeholder").document(mAuth.getUid()).set(mødeholder);

                    //sender verificering mail
                    sendEmailVeri(akt);

                    // Kalder onUserCreated hvis der er en listener
                    if(userCreatedListener != null) userCreatedListener.onUserCreated(mAuth.getCurrentUser());

                }
                else{
                    Toast.makeText(akt, "Fejl",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void sendEmailVeri(final Activity akt) {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(akt, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(akt,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(akt,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void login(final Activity akt, String email, String password){
        //TODO: skal lige gennemgås
        if (getCurrentUser() == null){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(akt, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");

                                if (loginListener != null) loginListener.onLogin(getCurrentUser());

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(akt, "Forkert email og/eller password.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else
        if (loginListener != null) loginListener.onLogin(getCurrentUser());


    }



    public void setOnUserCreatedListener(FBOnUserCreatedListener userCreatedListener){
        this.userCreatedListener = userCreatedListener;
    }

    public void setOnLoginListener(FBOnLoginListener loginListener){
        this.loginListener = loginListener;
    }

    /*Nok ligegyldigt alligevel
    public  void setOnEmailVerificationSentListener(){

    }*/


}

