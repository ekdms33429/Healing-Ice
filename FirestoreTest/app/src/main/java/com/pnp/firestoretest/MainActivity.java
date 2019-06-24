package com.pnp.firestoretest; //본인 프로젝트 이름

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView textViewData;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.text_view_data);

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
                                    String temp = data.get("humidity").toString();              // snapshots에는 ADDED, MODIFIED, REMOVED 등등의 모든 정보가 같이 내려옴
                                    String humidity = data.get("temp").toString();              // for문 돌리면서 ADDED 내의 조건(온도,습도,체크타임)이 나올때까지 찾음
                                    String checktime1 = data.get("checktime").toString();       // 값을 텍스트뷰에 찍어줌.

                                    textViewData.setText("\nhumidity : " + humidity + " %" + "\n"+ "temp : "+ temp + " ℃" + "\n\n" + "LastCheckTime : \n" + checktime1);

                                    break;
                                case MODIFIED: continue;
                                case REMOVED: continue; //remove()는 업데이트 그만 받고 싶으실 떄 호출
                            }

                        }
                    }
                });
    }
}
