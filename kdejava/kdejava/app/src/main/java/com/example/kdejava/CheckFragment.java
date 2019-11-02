package com.example.kdejava;


<<<<<<< HEAD
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
=======
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
<<<<<<< HEAD


import com.github.lzyzsd.circleprogress.ArcProgress;

import com.google.firebase.firestore.DocumentChange;

=======
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
<<<<<<< HEAD

import com.google.firebase.firestore.QuerySnapshot;

=======
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

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
<<<<<<< HEAD
        db.collection("sensordata2").orderBy("checktime", Query.Direction.DESCENDING).limit(1)

=======
        db.collection("sensordata").orderBy("checktime", Query.Direction.DESCENDING).limit(1)
                // orderby() : 컬렉션을 순서대로 정렬해서 데이터를 받아오는 함수, checktime은 센서값 쏠때 시간
                // 시간의 흐름순으로 받아야되서 DESCENDING(내림차순), limit(1)는 최신 데이터 1개만 받는 함수
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

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
<<<<<<< HEAD
                                case ADDED:
                                    Map<String, Object> data = dc.getDocument().getData();
                                    String temp = data.get("temp").toString();
                                    String humidity = data.get("humidity").toString();
                                    String checktime1 = data.get("checktime").toString();
                                    Log.w("dddd", String.valueOf(data.get("checktime").getClass()));

                                    textViewData.setText("\nhumidity : " + humidity + " %" + "\n" + "temp : " + temp + " ℃" + "\n\n" + "LastCheckTime : \n" + checktime1);

=======
                                case ADDED:                                                     // firestore db에 변화가 일어남 -> onEvent 수행
                                    Map<String, Object> data = dc.getDocument().getData();      // onEvent 할때 QuerySnapshot에 DB에서 생긴 변화들이 snapshots으로 들어옴
                                    String temp = data.get("temp").toString();              // snapshots에는 ADDED, MODIFIED, REMOVED 등등의 모든 정보가 같이 내려옴
                                    String humidity = data.get("humidity").toString();              // for문 돌리면서 ADDED 내의 조건(온도,습도,체크타임)이 나올때까지 찾음
                                    String checktime1 = data.get("checktime").toString();       // 값을 텍스트뷰에 찍어줌.

                                    textViewData.setText("\nhumidity : " + humidity + " %" + "\n" + "temp : " + temp + " ℃" + "\n\n" + "LastCheckTime : \n" + checktime1);
                                    //bar.setMax(100);
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                                    int temp2=(int)Double.valueOf(temp).doubleValue();
                                    int temp3=(int)Double.valueOf(humidity).doubleValue();
                                    bar1.setProgress(temp2);
                                    bar2.setProgress(temp3);
                                    break;
<<<<<<< HEAD

                                case MODIFIED:
                                    continue;

                                case REMOVED:
                                    continue;
=======
                                case MODIFIED:
                                    continue;
                                case REMOVED:
                                    continue; //remove()는 업데이트 그만 받고 싶으실 떄 호출
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                            }

                        }
                    }
                });
<<<<<<< HEAD

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
=======
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseMessaging.getInstance().setAutoInitEnabled(true); */
        return v;
    }
}
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
