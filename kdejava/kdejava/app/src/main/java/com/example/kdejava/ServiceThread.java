package com.example.kdejava;


import android.os.Handler;
import android.support.annotation.Nullable;


import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ServiceThread extends Thread{


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Handler handler;
    boolean isRun = true;




    public ServiceThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }



    public void run(){

        while(isRun){
            db.collection("sensordata2").orderBy("checktime", Query.Direction.DESCENDING).limit(1)


                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                switch (dc.getType()) {
                                    case ADDED:
                                        Map<String, Object> data = dc.getDocument().getData();
                                        String temp = data.get("temp").toString();
                                        String humidity = data.get("humidity").toString();

                                        int t=(int)Double.valueOf(temp).doubleValue();

                                        if(t>=50) {
                                            handler.sendEmptyMessage(0);
                                        }

                                        break;

                                    case MODIFIED:
                                        continue;

                                    case REMOVED:
                                        continue;
                                }

                            }
                        }
                    });

            try{
                Thread.sleep(10000);
            }catch (Exception e) {}

        }
    }
}
