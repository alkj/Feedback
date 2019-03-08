package dk.spinoff.apps.feedback_app.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.spinoff.apps.feedback_app.R;

public class ProgressDialog extends AlertDialog {

    private TextView textView;
    private CharSequence msg;
    private int count = 0;
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

        textView = findViewById(R.id.progress_txt);

        init();
    }

    private void init() {
        anim = new Runnable() {
            @Override
            public void run() {
                while (running) {

                    final String extra;
                    switch (++count) {
                        case 1:
                            extra = ".";
                            break;
                        case 2:
                            extra = "..";
                            break;
                        case 3:
                            extra = "...";
                            break;
                        default:
                            count = 0;
                            extra = "";
                    }

                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(msg + extra);
                        }
                    });

                    try {
                        Thread.sleep(800);
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
