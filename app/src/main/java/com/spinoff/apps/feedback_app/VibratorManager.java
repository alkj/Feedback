package com.spinoff.apps.feedback_app;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import static android.content.Context.VIBRATOR_SERVICE;

public class VibratorManager {
    public static final long[] FEJL_VIB = {0, 100, 50, 100};

    public static void vibrerMønster(Context context, long[] timings, int gentag){
        if (context == null)
            return;

        Vibrator vibrator = (android.os.Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(VibrationEffect.createWaveform(timings, gentag));
        else
            vibrator.vibrate(timings, -1);
    }
}
