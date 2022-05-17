package com.example.lockedshow.app;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

import com.example.lockedshow.services.MyService;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initServices();
    }

    private void initServices() {
        // 启动服务的地方
        Intent myService = new Intent(getApplicationContext(), MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(myService);
        } else {
            startService(myService);
        }
    }
}
