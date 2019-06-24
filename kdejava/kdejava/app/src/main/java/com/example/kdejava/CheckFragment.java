package com.example.kdejava;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

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
        db.collection("sensordata").orderBy("checktime", Query.Direction.DESCENDING).limit(1)
                // orderby() : 컬렉션을 순서대로 정렬해서 데이터를 받아오는 함수, checktime은 센서값 쏠때 시간
                // 시간의 흐름순으로 받아야되서 DESCENDING(내림차순), limit(1)는 최신 데이터 1개만 받는 함수

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
                                case ADDED:                                                     // firestore db에 변화가 일어남 -> onEvent 수행
                                    Map<String, Object> data = dc.getDocument().getData();      // onEvent 할때 QuerySnapshot에 DB에서 생긴 변화들이 snapshots으로 들어옴
                                    String temp = data.get("temp").toString();              // snapshots에는 ADDED, MODIFIED, REMOVED 등등의 모든 정보가 같이 내려옴
                                    String humidity = data.get("humidity").toString();              // for문 돌리면서 ADDED 내의 조건(온도,습도,체크타임)이 나올때까지 찾음
                                    String checktime1 = data.get("checktime").toString();       // 값을 텍스트뷰에 찍어줌.

                                    textViewData.setText("\nhumidity : " + humidity + " %" + "\n" + "temp : " + temp + " ℃" + "\n\n" + "LastCheckTime : \n" + checktime1);
                                    //bar.setMax(100);
                                    int temp2=(int)Double.valueOf(temp).doubleValue();
                                    int temp3=(int)Double.valueOf(humidity).doubleValue();
                                    bar1.setProgress(temp2);
                                    bar2.setProgress(temp3);
                                    break;
                                case MODIFIED:
                                    continue;
                                case REMOVED:
                                    continue; //remove()는 업데이트 그만 받고 싶으실 떄 호출
                            }

                        }
                    }
                });
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