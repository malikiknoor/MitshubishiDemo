package com.iknoortech.mitshubishidemo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.activity.MainActivity;
import com.iknoortech.mitshubishidemo.activity.feedback.FeedbackDetailActivity;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//Here notification is recieved from server
        try {
            sendNotification(remoteMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("Title");
        String description = remoteMessage.getData().get("Description");
        Intent intent;
        String text = remoteMessage.getData().get("body");

        if(remoteMessage.getNotification().getClickAction().equals("FEEDBACK")){
            intent = new Intent(this, FeedbackDetailActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("FeedbackId", text);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("channel-01", "Mitsubishi", importance);
            mChannel.enableVibration(true);

            mNotifyManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channel-01");
        mBuilder.setSmallIcon(R.drawable.ic_mitsubishi_electric_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mitsubishi_electric_logo))
                .setAutoCancel(true)
                .setPriority(IMPORTANCE_HIGH)
                .setContentTitle(title)
                .setContentText(description)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        mNotifyManager.notify(0, mBuilder.build());

    }

}
