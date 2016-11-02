package com.skywell.test.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetCheck{

    public static boolean networkChecking(Context sContext){
        ConnectivityManager cm =
                (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
