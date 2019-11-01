package com.example.kdejava;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;

import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class MonthGraphActivity extends AppCompatActivity {
    private static final String TAG = "MonthgraphActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_graph);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }


    public static class PlaceholderFragment extends Fragment {
        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec",};

        public final static String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun",};

        private LineChartView chartTop;
        private ColumnChartView chartBottom;

        private LineChartData lineData;
        private ColumnChartData columnData;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_monthgraph, container, false);
            chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);
            db.collection("selling").orderBy("checktime", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Map<String, Object> map = null;
                            HashMap<String, Integer> graphdata = new HashMap<String, Integer>();
                            for(int initial=0;initial<months.length;initial++){
                                graphdata.put(months[initial],0);
                            }

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    map = document.getData();
                                    String time = map.get("checktime").toString();
                                    int sell =  (int)Float.parseFloat(map.get("selling").toString());
                                    String[] timestamp = time.split(" ");
                                    if (graphdata.containsKey(timestamp[1])) {
                                        graphdata.put(timestamp[1], graphdata.get(timestamp[1]) + sell);

                                    }
                                }
                            }
                            Set set = graphdata.entrySet();
                            Log.d(TAG, "[최종]: " + set);
                            generateColumnData(graphdata);
                        }
                    });

            generateInitialLineData();


            chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_bottom);
            return rootView;
        }


        private void generateColumnData(HashMap<String, Integer> map) {

            int numSubcolumns = 1;
            int numColumns = months.length;
            //x
            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            //y
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {
                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(map.get(months[i]), ChartUtils.pickColor()));
                }
                axisValues.add(new AxisValue(i).setLabel(months[i]));
                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
                Log.d(TAG, months[i] + "달의 selling 값: " + map.get(months[i]));
            }

            columnData = new ColumnChartData(columns);

            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));

            chartBottom.setColumnChartData(columnData);


            chartBottom.setOnValueTouchListener(new ValueTouchListener());

            chartBottom.setValueSelectionEnabled(true);

            chartBottom.setZoomType(ZoomType.HORIZONTAL);


        }


        private void generateInitialLineData() {
            int numValues = 7;

            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();
            for (int i = 0; i < numValues; ++i) {
                values.add(new PointValue(i, 0));
                axisValues.add(new AxisValue(i).setLabel(days[i]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(4));

            chartTop.setLineChartData(lineData);


            chartTop.setViewportCalculationEnabled(false);


            Viewport v = new Viewport(0, 3000, 6, 0);
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
        }

        private void generateLineData(HashMap<String, Integer> map, int color) {

            chartTop.cancelDataAnimation();

            Line line = lineData.getLines().get(0);
            line.setColor(color);

            try {
                for (PointValue value : line.getValues()) {
                    if(map.get(days[(int)value.getX()])==null) map.put(days[(int)value.getX()],0);

                    value.setTarget(value.getX(), map.get(days[(int)value.getX()]));
                    Log.d(TAG, "[꺾은선 그래프]key : " + value.getX() + " value : " + map.get(days[(int)value.getX()]));
                }
            } catch (NullPointerException e){
                String msg = "해당 데이터가 없습니다.";
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }finally {

                chartTop.startDataAnimation(300);
            }

        }

        public class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(final int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                final int color = value.getColor();
                db.collection("selling").orderBy("checktime", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Map<String, Object> linemap = null;
                                HashMap<String, Integer> linegraphdata = new HashMap<String, Integer>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        linemap = document.getData();
                                        String time = linemap.get("checktime").toString();
                                        int sell =  (int)Float.parseFloat(linemap.get("selling").toString());
                                        String timestamp = time.substring(0, 3);
                                        if (months[columnIndex].equals(time.substring(4, 7))) {
                                            if (linegraphdata.containsKey(timestamp)) {
                                                linegraphdata.put(timestamp, linegraphdata.get(timestamp) + sell);

                                            } else {
                                                linegraphdata.put(timestamp, sell);

                                            }
                                        }
                                    }
                                }
                                generateLineData(linegraphdata,color);
                            }
                        });
            }
            @Override
            public void onValueDeselected() {
                HashMap<String, Integer> data = new HashMap<String, Integer>();
                data.put("",0);
                generateLineData(data, ChartUtils.COLOR_GREEN);

            }
        }

    }
}