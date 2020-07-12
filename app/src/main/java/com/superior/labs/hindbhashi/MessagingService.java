package com.superior.labs.hindbhashi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("notification", "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d("notification", "Message data payload: " + remoteMessage.getData());
            try{
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMsg = data.getString("extra_info");
                Log.d("notification", "INfo " + jsonMsg);
            }

            catch (JSONException e){
                e.printStackTrace();
            }


            if (/* Check if data needs to be processed by long running job */ true) {
            } else {
            }

        }

        if (remoteMessage.getNotification() != null) {

            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String id = remoteMessage.getData().get("extra_info").toString();
            String action = remoteMessage.getNotification().getClickAction();
            Log.d("notification", "Message Notification Title: " + title);
            Log.d("notification", " Notification Body: " + body);
            Log.d("notification", " Action: " + action);


            showNotification(title,body,action,id);
        }

    }

    @Override
    public void onNewToken(String token) {
        Log.d("notification", "Refreshed token: " + token);

    }

    @Override
    public void onDeletedMessages() {

    }

    private void showNotification(String action, String title, String msgBody, String id){


        Intent intent = new Intent(getBaseContext(),ViewPostActivity.class);
        intent.putExtra("id",msgBody);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.site_logo)
                .setContentText(title)
                .setContentText(msgBody)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifBuilder.build());


    }
}


