package com.example.admin.feedback_app.aktiviteter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.feedback_app.FBListener;
import com.example.admin.feedback_app.mødeholder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebaseLogik {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public FirebaseUser getCurrent(){
        mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser();
    }

    public void insætMødeholderData(final mødeholder mødeholder, final Activity akt, final FBListener listener) {

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        mAuth.createUserWithEmailAndPassword(mødeholder.getEmail(),mødeholder.getPassword()).addOnCompleteListener(akt, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //sætter data over på firebase
                    mFirestore.collection("mødeholder").document(mAuth.getUid()).set(mødeholder);

                    //sender verificering mail
                    sendEmailVeri(akt);

                    listener.videre();

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


}

