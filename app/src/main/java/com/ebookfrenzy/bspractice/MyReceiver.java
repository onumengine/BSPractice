package com.ebookfrenzy.bspractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction() == "CUSTOM BROADCAST NI-YAGGA") {
            String message = intent.getStringExtra("extra1");
            Toast.makeText(context, "View with id " + message + " clicked", Toast.LENGTH_LONG).show();
        }
    }
}
