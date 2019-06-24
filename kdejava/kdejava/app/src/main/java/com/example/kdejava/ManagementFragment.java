package com.example.kdejava;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

//기기관리탭
public class ManagementFragment extends Fragment {
    public int number = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.activity_management,container,false);

        ProgressBar pow_progress_bar = (ProgressBar)v.findViewById(R.id.pow_progress);
        ProgressBar milk_progress_bar = (ProgressBar)v.findViewById(R.id.milk_progress);


        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference conditionRef = mRootRef.child("machine1/milk_weight");
        DatabaseReference conditionRef2 = mRootRef.child("machine1/power_amout");

// 우유량, 파우더량 보여주는 그래프
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String value = dataSnapshot.getValue(String.class);
                int value = dataSnapshot.getValue(int.class);

                ProgressBar progress_bar = (ProgressBar)v.findViewById(R.id.power_weight);
                TextView pow_text = (TextView)v.findViewById(R.id.power_value);

                progress_bar.setProgress(value);

                String val = String.valueOf(value);

                pow_text.setText(val);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        conditionRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String value = dataSnapshot.getValue(String.class);
                int value = dataSnapshot.getValue(int.class);

                ProgressBar progress_bar = (ProgressBar)v.findViewById(R.id.milk_weight);
                TextView milk_text = (TextView)v.findViewById(R.id.milk_value);

                progress_bar.setProgress(value);
                String val = String.valueOf(value);

                milk_text.setText(val);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        // 프로그레스바 버튼 클릭 이벤트 -> 파우더량 조절
        pow_progress_bar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                final SeekBar seek = new SeekBar(getActivity());
                final TextView value = new TextView(getActivity());
                final TextView seekBarValue = null;
                seek.setMax(10);

                dialog.setTitle("ICE MAKER");
                dialog.setMessage("파우더량을 조절해주세요");
                dialog.setView(seek);

                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // seekbar의 상태가 변경되었을 때 실행될 사항
                        // progress는 seekbar의 상태 값을 가진다.

                        number = seek.getProgress();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        number = seek.getProgress();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        number = seek.getProgress();
                    }
                });

                dialog.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity().getApplicationContext(), "파우더양 : " + number, Toast.LENGTH_LONG).show();
                                ProgressBar progress_bar = (ProgressBar)v.findViewById(R.id.pow_progress);
                                progress_bar.setProgress(number);
                            }
                        });
                dialog.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity().getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.show();

            }


        });

        milk_progress_bar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                final SeekBar seek = new SeekBar(getActivity());
                final TextView value = new TextView(getActivity());
                final TextView seekBarValue = null;
                seek.setMax(10);

                dialog.setTitle("ICE MAKER");
                dialog.setMessage("우유량을 조절해주세요");
                dialog.setView(seek);

                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // seekbar의 상태가 변경되었을 때 실행될 사항
                        // progress는 seekbar의 상태 값을 가진다.

                        number = seek.getProgress();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        number = seek.getProgress();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        number = seek.getProgress();
                    }
                });

                dialog.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity().getApplicationContext(), "우유양 : " + number, Toast.LENGTH_LONG).show();
                                ProgressBar progress_bar = (ProgressBar)v.findViewById(R.id.milk_progress);
                                progress_bar.setProgress(number);
                            }
                        });
                dialog.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity().getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.show();
            }


        });

        return v;
    }

}