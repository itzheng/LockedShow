package com.example.lockedshow.utils;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.lockedshow.MainActivity;
import com.example.lockedshow.R;

public class NotificationUtils {
    /**
     * 获取通知
     *
     * @param context
     * @param title
     * @param content
     * @param iconRes
     * @param intent
     * @param requestCode
     * @return
     */
    public static Notification getNotification(Context context, String title, String content,
                                               int iconRes,
                                               Intent intent, int requestCode) {
        String ID = context.getPackageName();    //这里的id里面输入自己的项目的包的路径
        String NAME = title + "";

        PendingIntent pendingIntent = null;
        if (intent != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                //Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified
                pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
            }
        }
        NotificationCompat.Builder notificationBuilder; //创建服务对象
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ID, NAME, manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            notificationBuilder = new NotificationCompat.Builder(context).setChannelId(ID);
        } else {
            notificationBuilder = new NotificationCompat.Builder(context);
        }
        notificationBuilder.setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(iconRes)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconRes))
                .setContentIntent(pendingIntent)
                .build();
        return notificationBuilder.build();
    }
}
