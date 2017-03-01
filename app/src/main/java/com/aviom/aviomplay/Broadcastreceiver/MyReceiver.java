package com.aviom.aviomplay.Broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.aviom.aviomplay.Activities.MainActivity;

/**
 * Created by JETIX on 3/1/2017.
 */

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isOnline(context)){
            Toast.makeText(context, "Internet Activate", Toast.LENGTH_LONG).show();
        }

    }
    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();//Active network info
        return (nInfo != null && nInfo.isConnected());
    }
}
