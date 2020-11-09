package com.ebookfrenzy.bspractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    MyReceiver receiver;
    MyBoundService myBoundService;
    boolean boundServiceIsBound;
    Messenger remoteService = null;
    boolean remoteServiceIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getEvil();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void getEvil()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                gotoEvilActivity();
            }
        }, 1000);
    }

    private void gotoEvilActivity()
    {
        startActivity(new Intent(this, EvilActivity.class));
    }

    private ServiceConnection boundServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            MyBoundService.InnerBinding binding = (MyBoundService.InnerBinding) iBinder;
            myBoundService = binding.getService();
            boundServiceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            boundServiceIsBound = false;
        }
    };

    private ServiceConnection remoteServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder)
        {
            remoteService = new Messenger(iBinder);
            remoteServiceIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName)
        {
            remoteService = null;
            remoteServiceIsBound = false;
        }
    };

    private void bindUpService()
    {
        Intent boundServiceIntent = new Intent(this, MyBoundService.class);
        bindService(boundServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void bindRemoteService()
    {
        Intent remoteServiceIntent = new Intent(this, MyRemoteService.class);
        bindService(remoteServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initReceiver()
    {
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CUSTOM BROADCAST NI-YAGGA");
        registerReceiver(receiver, intentFilter);
    }

    public void sendCustomBroadcast(View view)
    {
        Intent intent = new Intent(this, MyReceiver.class);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setAction("CUSTOM BROADCAST NI-YAGGA");
        intent.putExtra("extra1", view.getId());
        sendBroadcast(intent);
    }

    public void toastMessage(View view)
    {
        myBoundService.toastMe("WHATEVER YOU WANT");
    }

    public void remoteToast(View view)
    {
        if (!remoteServiceIsBound) return;

        Bundle bundle = new Bundle();
        bundle.putString("key1", "DARTH VADER");

        Message message = Message.obtain();
        message.setData(bundle);

        try
        {
            remoteService.send(message);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}