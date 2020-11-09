package com.ebookfrenzy.bspractice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyRemoteService extends Service
{
    final Messenger messenger = new Messenger(new InnerMessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return messenger.getBinder();
    }

    class InnerMessageHandler extends Handler
    {
        @Override
        public void handleMessage(@NonNull Message msg)
        {
            Bundle bundle = msg.getData();
            String dataString = bundle.getString("key1");
            Toast.makeText(getApplicationContext(), dataString, Toast.LENGTH_LONG).show();
        }
    }
}
