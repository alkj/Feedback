package com.example.admin.feedback_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

public class NetworkManager {

    /**
     * Checker om der er en ledig internetforbindelse
     *
     * @return true or false
     */
    public static boolean harInternet(Context context) {
        boolean netværk;

        //Checks if there's an internet connection
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        netværk = networkInfo != null && networkInfo.isConnected();

        return netværk;
    }
}
