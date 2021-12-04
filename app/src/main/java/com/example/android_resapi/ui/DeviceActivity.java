package com.example.android_resapi.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetThingShadow;
import com.example.android_resapi.ui.apicall.UpdateShadow;
import com.example.android_resapi.ui.dto.DataDto;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Button startGetBtn;
    Button stopGetBtn;
    private LineChart lineChart;  // 차트 시각화
    private LineChart lineChart2;

    public void responsedto(DataDto dataDto){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
/*
        lineChart = (LineChart)findViewById(R.id.chart_temp); // 차트 시각화
        lineChart2 = (LineChart)findViewById(R.id.chart_humid); // 차트 시각화

        List<Entry> temperatures = new ArrayList<>();
        List<Entry> humids = new ArrayList<>();


        TextView temperatureTextview= findViewById(R.id.desired_temp);
        int temperature = 0;
        if(temperatureTextview.getText().toString().equals("")) {
             temperature = 4;
        }else
             temperature = 10;
//        int humid = Integer.parseInt(findViewById(R.id.desired_temp).toString());

        temperatures.add(new Entry(1, temperature));
        temperatures.add(new Entry(2, temperature));
        temperatures.add(new Entry(3, temperature));
        temperatures.add(new Entry(4, temperature));
        temperatures.add(new Entry(5, temperature));


        humids.add(new Entry(1,temperature));
        humids.add(new Entry(2,temperature));
        humids.add(new Entry(3,temperature));
        humids.add(new Entry(4,temperature));
        humids.add(new Entry(5,temperature));
        humids.add(new Entry(6,temperature));


        LineDataSet lineDataSet = new LineDataSet(temperatures, "temperature");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);


        LineDataSet lineDataSet2 = new LineDataSet(humids, "humid");
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setCircleRadius(6);
        lineDataSet2.setCircleColor(Color.parseColor("#5F9EA0"));
        lineDataSet2.setCircleColorHole(Color.BLUE);
        lineDataSet2.setColor(Color.parseColor("#87CEFA"));
        lineDataSet2.setDrawCircleHole(true);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawHighlightIndicators(false);
        lineDataSet2.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");


        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis2 = lineChart2.getAxisLeft();
        yLAxis2.setTextColor(Color.BLACK);

        YAxis yRAxis2 = lineChart2.getAxisRight();
        yRAxis2.setDrawLabels(false);
        yRAxis2.setDrawAxisLine(false);
        yRAxis2.setDrawGridLines(false);

        Description description2 = new Description();
        description2.setText("");



        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();


        lineChart2.setDoubleTapToZoomEnabled(false);
        lineChart2.setDrawGridBackground(false);
        lineChart2.setDescription(description);
        lineChart2.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart2.invalidate();

        */
        //----------------------------------------

        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingShadowURL");

        startGetBtn = findViewById(R.id.startGetBtn); // 조회 시작
        startGetBtn.setEnabled(true);
        startGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       new GetThingShadow(DeviceActivity.this, urlStr).execute();
                                   }
                               },
                        0, 2000);

                startGetBtn.setEnabled(false);
                stopGetBtn.setEnabled(true);
            }
        });

        stopGetBtn = findViewById(R.id.stopGetBtn);
        stopGetBtn.setEnabled(false);
        stopGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();
                startGetBtn.setEnabled(true);
                stopGetBtn.setEnabled(false);
            }
        });

        Button updateBtnOn = findViewById(R.id.updateBtnOn); //  ON button
        updateBtnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText edit_led = findViewById(R.id.edit_led); // LED(에어컨 ON/OFF)  상태 제어

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String ledInput = "ON"; //edit_led.getText().toString();
                    if (ledInput != null && !ledInput.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "LED");
                        tag1.put("tagValue", ledInput);

                        jsonArray.put(tag1);
                    }



                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG, "payload=" + payload);
                if (payload.length() > 0)
                    new UpdateShadow(DeviceActivity.this, urlStr).execute(payload);
                else
                    Toast.makeText(DeviceActivity.this, "변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateBtnOff = findViewById(R.id.updateBtnOff); //  ON button
        updateBtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText edit_led = findViewById(R.id.edit_led); // LED(에어컨 ON/OFF)  상태 제어

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String ledInput = "OFF"; //edit_led.getText().toString();
                    if (ledInput != null && !ledInput.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "LED");
                        tag1.put("tagValue", ledInput);

                        jsonArray.put(tag1);
                    }



                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG, "payload=" + payload);
                if (payload.length() > 0)
                    new UpdateShadow(DeviceActivity.this, urlStr).execute(payload);
                else
                    Toast.makeText(DeviceActivity.this, "변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearTextView() {
        TextView reported_ledTV = findViewById(R.id.reported_led);
        TextView reported_tempTV = findViewById(R.id.reported_temp);
        reported_tempTV.setText("");
        reported_ledTV.setText("");
    }

}


