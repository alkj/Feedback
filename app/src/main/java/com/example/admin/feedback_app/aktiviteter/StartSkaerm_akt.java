package com.example.admin.feedback_app.aktiviteter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.feedback_app.Møde;
import com.example.admin.feedback_app.PersonData;
import com.example.admin.feedback_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StartSkaerm_akt extends AppCompatActivity implements View.OnClickListener {

    private Button login_btn, feedback_btn;
    private EditText mødeId_editTxt;
    private FirebaseAuth firebaseAuth;
    PersonData personData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startskaerm);

        //Persondata som holder på det møde der skal gives feedback til
        personData = PersonData.getInstance();

        //Knapper
        login_btn = (Button)findViewById(R.id.startskaerm_login_btn);
        feedback_btn = (Button)findViewById(R.id.startskaerm_feedback_btn);

        login_btn.setOnClickListener(this);
        feedback_btn.setOnClickListener(this);

        //Input felt
        mødeId_editTxt = (EditText)findViewById(R.id.startskærm_editTxt);
    }

    @Override
    public void onClick(View view) {
        if (view == login_btn){
            //Starter login aktiviteteten
            Intent intent = new Intent(this, Login_akt.class);
            startActivity(intent);
        }
        else if ( view == feedback_btn) {
            //TODO: indlæs inputet fra editText'en og finde det tilhørende møde
            String mødeID = mødeId_editTxt.getText().toString();

            //updateProgressDialog("Henter møderne");
            FirebaseFirestore.getInstance().collection("Møder")
                    .whereEqualTo("mødeIDtildeltager", mødeID)
                    .get()
                    .addOnCompleteListener(new FindMødeListener());

            //TODO: Få lavet så der kun åbnes feedback hvis der findes et møde med dette loginID (lige nu går alt igennem selvom den faktisk finder et møde og smider det ind i persondata)
            if (personData.getFeedbackTilDetteMøde() == null) {
                Toast.makeText(this, "Forkert møde-ID ", Toast.LENGTH_SHORT).show();
            } else {

            //Starter feedback aktiviteten
            Intent intent = new Intent(this, GivFeedback_akt.class);
            //intent.putExtra("MØDEID",mødeId_editTxt.getText());
            startActivity(intent);
            //Log.d("debug, virker det",personData.getFeedbackTilDetteMøde().getMødeID());

        }
        }
    }

    class FindMødeListener implements OnCompleteListener<QuerySnapshot> {

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String mødeID = document.get("mødeID").toString();
                    Møde mødet = new Møde();
                    mødet.setMødeID(mødeID);
                    personData.setFeedbackTilDetteMøde(mødet);
                    Log.d("debug, hvad bliver", "Mødeid'et bliver: " + mødeID);

                    //personData.tilføjMøde(mødeObj);

                    //Log.d(TAG, "navn fra firebase: " + document.get("navn").toString());
                    //Log.d(TAG, "mødelistens navn: " + mødeObj.getNavn());
                    //Log.d(TAG, document.getId() + " => " + document.getData());
                }


                //næsteSide();
            } else {
                Log.d("debug, det er lrt", "hvorfor fejler den aldrig");
            }

        }
    }
}
