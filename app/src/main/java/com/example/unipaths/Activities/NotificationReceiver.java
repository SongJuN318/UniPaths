package com.example.unipaths.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.createNotificationChannel(context);

        String title = "Don't miss a deadline!";
        String content = "Remember to sign up for your scholarships!";

        NotificationHelper.createNotification(context, title, content);
    }
}
