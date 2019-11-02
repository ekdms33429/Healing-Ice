package com.example.kdejava;

import android.support.v4.app.Fragment;
<<<<<<< HEAD

=======
import android.support.v7.app.AppCompatActivity;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
<<<<<<< HEAD

import android.widget.ImageButton;
=======
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectmodeFragment extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("machine1/mode_status");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
        View v = (View) inflater.inflate(R.layout.selectmode_fragment,container,false);
        final ImageButton ice_btn = (ImageButton) v.findViewById(R.id.button_ice);
        final ImageButton drink_btn = (ImageButton) v.findViewById(R.id.button_drink);
=======

        View v = (View) inflater.inflate(R.layout.selectmode_fragment,container,false);
        final ImageButton ice_btn = (ImageButton) v.findViewById(R.id.button_ice);
        final ImageButton drink_btn = (ImageButton) v.findViewById(R.id.button_drink);
        final ImageButton clean_btn = (ImageButton) v.findViewById(R.id.button_clean);

>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
<<<<<<< HEAD

=======
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
                if (text.equals("ice_off"))
                    ice_btn.setImageResource(R.drawable.iceoff);
                else if (text.equals("drink_off"))
                    drink_btn.setImageResource(R.drawable.drinkoff);
<<<<<<< HEAD
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        ice_btn.setOnClickListener(new View.OnClickListener() { // ice버튼 이미지 클릭시 발생하는 이벤트
=======
                else if (text.equals("clean_off"))
                    clean_btn.setImageResource(R.drawable.cleanoff);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        ice_btn.setOnClickListener(new View.OnClickListener() {
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
            @Override
            public void onClick(View v) {
                ice_btn.setImageResource(R.drawable.iceon);
                conditionRef.setValue("ice_on");
            }
        });
        drink_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drink_btn.setImageResource(R.drawable.drinkon);
                conditionRef.setValue("drink_on");
            }
        });
<<<<<<< HEAD
=======
        clean_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean_btn.setImageResource(R.drawable.cleanon);
                conditionRef.setValue("clean_on");
            }
        });
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9

        return v;
    }


}