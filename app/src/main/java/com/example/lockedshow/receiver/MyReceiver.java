package com.example.lockedshow.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.lockedshow.MainActivity;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        return intentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive: " + intent.getAction());
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                Log.w(TAG, "onReceive: " + "startActivity");
                Intent act = new Intent(context, MainActivity.class);
                act.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(act);
                break;
            case Intent.ACTION_SCREEN_OFF:
                break;
            case Intent.ACTION_USER_PRESENT:
                //解锁成功
                break;
        }
    }
}
