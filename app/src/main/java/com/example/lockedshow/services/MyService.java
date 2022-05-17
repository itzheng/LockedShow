package com.example.lockedshow.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.lockedshow.MainActivity;
import com.example.lockedshow.R;
import com.example.lockedshow.receiver.MyReceiver;
import com.example.lockedshow.utils.NotificationUtils;

import java.io.FileDescriptor;

public class MyService extends Service {
    private static final String TAG = "MyService";
    IBinder iBinder = new MyBinder();
    MyReceiver myReceiver = new MyReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "onCreate: ");
        registerReceiver(myReceiver, MyReceiver.getIntentFilter());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG, "onBind: ");
        return iBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.w(TAG, "onRebind: ");
    }

    Notification notification;

    public void initNotification() {
        if (notification == null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            notification = NotificationUtils.getNotification(getApplicationContext(),
                    getString(R.string.app_name), "点击打开app",
                    R.mipmap.ic_launcher, intent, 0x11);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand: ");
        initNotification();
        startForeground(10, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy: ");
        stopForeground(true);
        unregisterReceiver(myReceiver);

    }
}
