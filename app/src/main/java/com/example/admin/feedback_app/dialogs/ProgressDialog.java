package com.example.admin.feedback_app.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.feedback_app.R;

/**
 * @author  Nils DAvid Rasamoel
 * Dum dialog som konstant loader
 */
public class ProgressDialog extends AlertDialog {

    private TextView progressTxt;
    private ProgressBar progressBar;
    private CharSequence msg;
    private Runnable anim;
    private boolean running;

    public ProgressDialog(@NonNull Context context, CharSequence msg) {
        super(context);
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.progressdialog_layout);
        setCanceledOnTouchOutside(false);

        progressTxt = findViewById(R.id.progress_txt);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(1);

        init();
    }

    private void init() {
        anim = new Runnable() {
            @Override
            public void run() {
                while (running) {

                    progressTxt.post(new Runnable() {
                        @Override
                        public void run() {
                            progressTxt.setText(msg);
                        }
                    });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        };

    }

    @Override
    public void setMessage(CharSequence msg) {
        this.msg = msg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        running = true;
        new Thread(anim).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        running = false;
    }

}
