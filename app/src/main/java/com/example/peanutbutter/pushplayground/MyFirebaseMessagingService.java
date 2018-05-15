package com.example.peanutbutter.pushplayground;

import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
        super();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "TAK")
                .setContentTitle("PUSH received")
                .setContentText("PUSH received")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("PUSH received"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.build();

        super.onMessageReceived(remoteMessage);

    }
}
