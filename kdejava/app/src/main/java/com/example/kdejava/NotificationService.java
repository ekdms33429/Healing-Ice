package com.example.kdejava;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.os.IBinder;

import android.support.v4.app.NotificationCompat;

import android.app.Notification;

import android.media.RingtoneManager;
import android.os.Handler;

import com.google.firebase.firestore.FirebaseFirestore;


public class NotificationService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    public void onDestroy() {
        thread.stopForever();
        thread = null;
    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            String channelId = "channel";
            String channelName = "Channel Name";

            NotificationManager notifManager = (NotificationManager) getSystemService  (Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
                notifManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);


            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            int requestID = (int) System.currentTimeMillis();

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext()
                    , requestID
                    , notificationIntent
                    , PendingIntent.FLAG_UPDATE_CURRENT);


            builder.setContentTitle("HealingIce")
                    .setContentText("기기 내의 온도가 너무 높아요!")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)

                    .setSound(RingtoneManager
                            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentIntent(pendingIntent);
            notifManager.notify(0, builder.build());


        }


    }
}


