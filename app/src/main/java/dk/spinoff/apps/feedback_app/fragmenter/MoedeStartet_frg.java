package dk.spinoff.apps.feedback_app.fragmenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.spinoff.apps.feedback_app.R;
import dk.spinoff.apps.feedback_app.aktiviteter.IkkeAfholdtMoede_akt;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MoedeStartet_frg extends Fragment implements View.OnClickListener {

    private TextView
            moedeID, moedeNavn, moedeFormaal, inviterede, fremmoedte,
            planlagtStarttid, planlagtSluttid, aktuelStarttid;

    private String aktuelSluttid;

    private String
            ID, navn, formaal, antalInviterede, startTid, slutTid;

    private Chronometer forloebtTid;

    private Button afslutMoede;

    IkkeAfholdtMoede_akt ikkeAfholdtMoede_akt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moede_startet_frg, container, false);

        moedeID = view.findViewById(R.id.tvMødeIDStartetMøde);
        moedeNavn = view.findViewById(R.id.tvMødeStartetMødeNavn);
        moedeFormaal = view.findViewById(R.id.tvFormål);
        inviterede = view.findViewById(R.id.tvMødeStartetInviterede);
        fremmoedte = view.findViewById(R.id.tvMødeStartetFremmødte);
        planlagtStarttid = view.findViewById(R.id.tvMødeStartetPlanlagtStarttid);
        planlagtSluttid = view.findViewById(R.id.tvMødeStartetPlanlagtSluttid);
        aktuelStarttid = view.findViewById(R.id.tvMødeStartetAktuelStartTid);
        forloebtTid = view.findViewById(R.id.tvMødeStartetForløbtTid);

        // TODO: gemme 'aktuel starttid' i firabase
        aktuelStarttid.setText(getCurrentTime());
        forloebtTid.start();

/*
        ID = this.getArguments().getString("Møde ID");
        navn = this.getArguments().getString("Møde Navn");
        formaal = this.getArguments().getString("Møde Formål");
        antalInviterede = this.getArguments().getString("Inviterede");
        startTid = this.getArguments().getString("Planlagt Starttid");
        slutTid = this.getArguments().getString("Planlagt Sluttid");



        moedeID.setText(ID);
        moedeNavn.setText(navn);
        moedeFormaal.setText(formaal);
        inviterede.setText(antalInviterede);
        planlagtStarttid.setText(startTid);
        planlagtSluttid.setText(slutTid);
*/
        afslutMoede = view.findViewById(R.id.mødeStartet_Afslut_knap);
        afslutMoede.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == afslutMoede) {
            // TODO: gemme 'aktuel sluttid' i firebase
            aktuelSluttid = getCurrentTime();
            // TODO: afslut fragmentet og afslut aktivitet
            // TODO: gør mødestatus til 'Afsholdt' i firebase
            getActivity().finish();

        }
    }

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDato = mdformat.format(calendar.getTime());
        return strDato;
    }

}
