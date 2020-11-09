package com.ebookfrenzy.bspractice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyBoundService extends Service
{
    final IBinder myBinding = new InnerBinding();

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return myBinding;
    }

    public class InnerBinding extends Binder
    {
        MyBoundService getService()
        {
            return MyBoundService.this;
        }
    }

    public void toastMe(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
