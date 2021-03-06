package com.example.kdejava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import android.widget.TextView;



public class ListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        CardView mach1 = (CardView)findViewById(R.id.machine1);
        CardView mach2 = (CardView)findViewById(R.id.machine2);
        TextView userid = (TextView) findViewById(R.id.userid) ;

        Intent intent2 = getIntent();
        String myId = intent2.getStringExtra("userid");

        userid.setText(myId);
        mach1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Machine1Activity.class);
                startActivity(intent);
                finish();
            }
        });
        mach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),Machine2Activity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

}
