package com.example.peanutbutter.pushplayground;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String CHANELL_ID_DEFAULT = "CHANELL_ID_DEFAULT";

    public MyFirebaseMessagingService() {
        super();

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = getApplication().getApplicationContext().getSystemService(NotificationManager.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(new NotificationChannel(CHANELL_ID_DEFAULT, "TITLE", NotificationManager.IMPORTANCE_DEFAULT));
            }
        }
        processNotification(remoteMessage.getData(), remoteMessage.getMessageId().hashCode());
    }

    public void processNotification(Map<String, String> data, int notificationId) {
        // This will describe type of notification
        String entity = data.get("entity");
        String title = data.get("title");
        Intent contentIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(this, 0 /* Request code */, contentIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            displayNotification(title, notificationId, pendingContentIntent);
        }


        /*
        val notification = notification(this) {
            setSmallIcon(R.drawable.ic_notification)
            setContentIntent(pendingContentIntent)
            setDeleteIntent(pendingDeleteIntent)
            color = R.color.accent.toColor()
            setNumber(rows.size)
            setAutoCancel(true)
            setStyle(inboxStyle)
            setChannelId(channelId)
            setContentTitle(groupName)
            setContentText(rows.last())
        }

        val notificationId = groupId.hashCode()
        app().notificationManager.notify(notificationId, notification)
        */
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void displayNotification(String title, int notificationId, PendingIntent contentIntent) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setSummaryText(title);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANELL_ID_DEFAULT);
        notification.setStyle(inboxStyle);
        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        notification.setAutoCancel(true);
        notification.setNumber(6);
        notification.setPriority(NotificationCompat.PRIORITY_MAX);
        notification.setContentText("HARDCODED Text");
        notification.setContentTitle("HARDCODED Title");
        notification.setChannelId(CHANELL_ID_DEFAULT);
        notification.setContentIntent(contentIntent);

        //try {
        //  if (getPackageManager().getPackageInfo(getPackageName(), 0).versionCode >=23) {
        NotificationManager notificationManager = getApplication().getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.notify(notificationId, notification.build());
        //}
        //} catch (PackageManager.NameNotFoundException e) {
        // Log.e("TAG", "NAME_NOT_FOUND");
        //}


    }
}
