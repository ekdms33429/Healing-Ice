package com.example.kdejava;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.media.RingtoneManager;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.lzyzsd.circleprogress.ArcProgress;

import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.firebase.firestore.QuerySnapshot;


import java.util.Map;


public class CheckFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "CheckFragment";
    private TextView textViewData;
    ArcProgress bar1,bar2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.check_fragment_, container, false);
        textViewData = v.findViewById(R.id.text_view_data);
        bar1=v.findViewById(R.id.temp_progress);
        bar2=v.findViewById(R.id.hum_progress);
        db.collection("sensordata2").orderBy("checktime", Query.Direction.DESCENDING).limit(1)


                .addSnapshotListener(new EventListener<QuerySnapshot>() { //db 업데이트가 있을 때마다 EventListener 안의 onEvent가 호출되는 방식
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Map<String, Object> data = dc.getDocument().getData();
                                    String temp = data.get("temp").toString();
                                    String humidity = data.get("humidity").toString();
                                    String checktime1 = data.get("checktime").toString();
                                    Log.w("dddd", String.valueOf(data.get("checktime").getClass()));

                                    textViewData.setText("\nhumidity : " + humidity + " %" + "\n" + "temp : " + temp + " ℃" + "\n\n" + "LastCheckTime : \n" + checktime1);

                                    int temp2=(int)Double.valueOf(temp).doubleValue();
                                    int temp3=(int)Double.valueOf(humidity).doubleValue();
                                    bar1.setProgress(temp2);
                                    bar2.setProgress(temp3);
                                    break;

                                case MODIFIED:
                                    continue;

                                case REMOVED:
                                    continue;
                            }

                        }
                    }
                });

        return v;
    }

    private void createNotification() {


        String channelId = "channel";
        String channelName = "Channel Name";
        NotificationManager notifManager = (NotificationManager)getContext().getSystemService  (Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notifManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId);
        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent
                = PendingIntent.getActivity(getContext()
                , requestID
                , notificationIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentTitle("힐링아이스")
                .setContentText("온도가 너무높아요!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)

                .setSound(RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(android.R.drawable.btn_star)
                .setContentIntent(pendingIntent);
        notifManager.notify(0, builder.build());


    }

}
