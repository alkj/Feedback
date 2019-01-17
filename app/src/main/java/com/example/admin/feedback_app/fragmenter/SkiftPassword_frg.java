package com.example.admin.feedback_app.fragmenter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class SkiftPassword_frg extends Fragment implements View.OnClickListener {

    private static final String TAG = "SkiftPassword";

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private PersonData personData;

    private EditText editTextGammeltPassword, editTextNytPassword1, editTextNytPassword2;
    private TextView textViewBrugernavn;
    private Button buttonSkiftPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rod = inflater.inflate(R.layout.fragment_skift_password, container, false);

        personData = PersonData.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        editTextGammeltPassword = rod.findViewById(R.id.skift_password_gammeltpassword_editText);
        editTextNytPassword1 = rod.findViewById(R.id.skift_password_password1_editText);
        editTextNytPassword2 = rod.findViewById(R.id.skift_password_password2_editText);
        textViewBrugernavn = rod.findViewById(R.id.skift_password_brugernavn_textView);
        buttonSkiftPassword = rod.findViewById(R.id.skift_password_gem_button);

        buttonSkiftPassword.setOnClickListener(this);

        textViewBrugernavn.setText(personData.getMødeholder().getEmail());

        return rod;
    }

    @Override
    public void onClick(View v) {
        if (v == buttonSkiftPassword) {
            String gammeltPassword, nytPassword1, nytPassword2;
            gammeltPassword = editTextGammeltPassword.getText().toString();
            nytPassword1 = editTextNytPassword1.getText().toString();
            nytPassword2 = editTextNytPassword2.getText().toString();

            //hvis der er problemer med passwords. det gamle skal være korrekt. de nye skal være ens, og længere ens 6 tegn.
            if (!gammeltPassword.equals(personData.getMødeholder().getPassword())) {
                Toast.makeText(this.getActivity(), "forkert password", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: :" + personData.getMødeholder().getPassword() + ": :" + gammeltPassword + ": ");
            } else if (!nytPassword1.equals(nytPassword2)) {
                Toast.makeText(this.getActivity(), "de to passwords er ikke ens", Toast.LENGTH_SHORT).show();
            } else if (nytPassword1.length() < 6) {
                Toast.makeText(this.getActivity(), "skriv et password der er længere end 6 tegn", Toast.LENGTH_SHORT).show();
            } else {
                //hvis der ikke er problemer, skal passwordet sættes.
                personData.getMødeholder().setPassword(nytPassword1);
                Log.d(TAG, "onClick: password accepted, and set in object");
                firebaseFirestore.collection("Mødeholder").document(firebaseAuth.getUid()).set(personData.getMødeholder());
                Log.d(TAG, "onClick: password set in firebase");
                this.getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(this.getActivity(), "Password ændret", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
