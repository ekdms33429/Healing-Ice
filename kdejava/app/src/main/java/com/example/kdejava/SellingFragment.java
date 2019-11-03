package com.example.kdejava;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

}