package com.example.kdejava;

<<<<<<< HEAD
import android.app.DatePickerDialog;
import android.content.Intent;
=======
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;



public class SellingFragment extends Fragment {
    static final String[] LIST_MENU = {"월별 판매량(요일 기준)", "날짜별 판매량(시간 기준)"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selling_fragment_,container,false);
        ListView listview = (ListView) v.findViewById(R.id.list_chart);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,LIST_MENU);
        listview.setAdapter(listViewAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                switch (pos) {
                    case 0:
                        Intent intent = new Intent(getActivity(), MonthGraphActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        DatePickerDialog dialog = new DatePickerDialog(getContext(), listener, 2019, 7, 22);
                        dialog.show();
                        break;
                }


                }
        });
        return v;
    }
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String date = String.format("%02d",dayOfMonth);
            String month = Integer.toString(monthOfYear+1);
            Intent intent = new Intent(getActivity(), TimegraphActivity.class);
            Toast.makeText(getActivity(), year + "년 " + month + "월 " + date + "일", Toast.LENGTH_SHORT).show();
            intent.putExtra("select_year",Integer.toString(year));
            intent.putExtra("select_month", month);
            intent.putExtra("select_day",date);
            startActivity(intent);
        }
    };

=======

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class SellingFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selling_fragment_,container,false);
        BarChart mBarChart = (BarChart) v.findViewById(R.id.barchart);

        mBarChart.addBar(new BarModel(2.3f, 0xFF123456));
        mBarChart.addBar(new BarModel(2.f,  0xFF343456));
        mBarChart.addBar(new BarModel(3.3f, 0xFF563456));
        mBarChart.addBar(new BarModel(1.1f, 0xFF873F56));
        mBarChart.addBar(new BarModel(2.7f, 0xFF56B7F1));
        mBarChart.addBar(new BarModel(2.f,  0xFF343456));
        mBarChart.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        mBarChart.addBar(new BarModel(4.f,  0xFF1BA4E6));

        mBarChart.startAnimation();
        return v;
    }
>>>>>>> 706198c7c6cbc69281001edfeca32ba8a54a40b9
}