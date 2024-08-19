package com.dilip.newsline;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String CHANNEL_ID = "DefaultChannel";
    private static final String CHANNEL_NAME = "Default Channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + messageTitle);
            Log.d(TAG, "Message Notification Body: " + messageBody);
            sendNotification(messageTitle, messageBody);
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // Send token to your server or save it locally
    }

    private void sendNotification(String title, String messageBody) {
        // Check if the notification permission has been granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.w(TAG, "Notification permission not granted. Unable to show notification.");
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification) // Ensure ic_notification exists
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH) // High priority for better visibility
                        .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI) // Default notification sound
                        .setVibrate(new long[]{1000, 1000}); // Vibration pattern

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH); // High importance for Oreo and above
            channel.setDescription("Channel for default notifications");
            notificationManager.createNotificationChannel(channel);
        }

        Log.d(TAG, "Attempting to show notification: " + title + " - " + messageBody);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
