package com.example.kdejava;


import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import lecho.lib.hellocharts.gesture.ZoomType;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;

import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;

import lecho.lib.hellocharts.view.LineChartView;

//시간별 그래프
public class TimegraphActivity extends AppCompatActivity {
    private static final String TAG = "TimegraphActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timegraph);



        Intent intent = getIntent();
        PlaceholderFragment fragInfo = new PlaceholderFragment();
        Bundle bundle = new Bundle();

        bundle.putString("select_month", intent.getExtras().getString("select_month") );
        bundle.putString("select_day", intent.getExtras().getString("select_day"));
        bundle.putString("select_year", intent.getExtras().getString("select_year") );

        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container, fragInfo);
            fragInfo.setArguments(bundle);
            fragmentTransaction.commit();
        }
    }



    public static class PlaceholderFragment extends Fragment {
        private FirebaseFirestore db = FirebaseFirestore.getInstance();

        public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec",};
        public final  String[] time = new String[]{"00", "01", "02", "03","04", "05", "06", "07", "08","09", "10", "11", "12",
                "13","14","15","16","17","18","19","20","21","22","23","24"};

        private LineChartView chartTop;
        private LineChartData lineData;
        private ColumnChartData columnData;
        private TextView name;

        public PlaceholderFragment() {}

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_timegraph, container, false);

            Bundle extra = getArguments();
            final String month= extra.getString("select_month");
            final String day= extra.getString("select_day");
            final String year= extra.getString("select_year");

            name = (TextView)rootView.findViewById(R.id.chart_name) ;
            name.setText(year+"년 "+month+"월 "+day+"일 시간별 그래프");

            chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);
            db.collection("selling").orderBy("checktime", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Map<String, Object> map = null;
                            HashMap<String, Integer> graphdata = new HashMap<String, Integer>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    map = document.getData();
                                    String time = map.get("checktime").toString();
                                    time = time.replace(" GMT+09:00", "");

                                    int sell =  (int)Float.parseFloat(map.get("selling").toString());
                                    String[] timestamp = time.split(" ");
                                    String[] selected_date = {months[Integer.parseInt(month)-1],day,year};

                                    String slctime =timestamp[3].substring(0,2);
                                    boolean judge = selected_date[0].equals(timestamp[1])&&selected_date[1].equals(timestamp[2])&&selected_date[2].equals(timestamp[4]);
                                    if(judge){
                                        if (graphdata.containsKey(slctime)) {
                                            graphdata.put(slctime, graphdata.get(slctime) + sell);
                                            Log.d(TAG, "[꺾은선][if]key : " + slctime + " value : " + graphdata.get(slctime));
                                        } else { //처음 들어올 때
                                            graphdata.put(slctime, sell);
                                            Log.d(TAG, "[꺾은선][else]key : " +slctime+ " value : " + graphdata.get(slctime));
                                        }
                                    }
                                }
                            }
                            Set set = graphdata.entrySet();
                            Log.d(TAG,"[최종]: "+ set);
                            generateColumnData(graphdata);
                        }
                    });
            return rootView;
        }


        private void generateColumnData(HashMap<String, Integer> map) {

            int numValues = time.length;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0; i < numValues; i++) {
                if(map.get(time[i])==null) map.put(time[i],0);
                values.add(new PointValue(i, map.get(time[i])));
                axisValues.add(new AxisValue(i).setLabel(time[i]));
                Log.d(TAG, "[x축 값] : " +i+ " [y축 값] : " +map.get(time[i]));

            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

            chartTop.setLineChartData(lineData);

            chartTop.setViewportCalculationEnabled(false);


            Viewport v = new Viewport(0, 100, 25, 0);
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
        }
    }
}