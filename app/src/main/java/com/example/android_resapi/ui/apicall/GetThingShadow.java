package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.DeviceActivity;
import com.example.android_resapi.ui.dto.DataDto;
import com.example.android_resapi.ui.service.DataService;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class GetThingShadow extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    DataDto dataDto;
    DataService dataService;

    static int cnt = 0;

    TextView reported_ledTV;
    TextView reported_tempTV;
    TextView reported_humid;
    TextView reported_airCond;

    Map<String, String> state;

    LineChart lineChart;  // 차트 시각화
    LineChart lineChart2;


    public GetThingShadow(Activity activity, String urlStr) { // activity == DeviceActivity
        super(activity);
        this.urlStr = urlStr;
    }



    @Override
    protected void onPreExecute() {
        try {
            super.onPreExecute();
            Log.e(TAG, urlStr);
            url = new URL(urlStr);

        } catch (MalformedURLException e) {
            Toast.makeText(activity, "URL is invalid:" + urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            activity.finish();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) { // GetRequest의 doInBackground 로 부터 전달받은 String
        if (jsonString == null)
            return;
        state = getStateFromJSONString(jsonString);
        reported_ledTV = activity.findViewById(R.id.reported_led);
        reported_tempTV = activity.findViewById(R.id.reported_temp);
        reported_airCond = activity.findViewById(R.id.reported_air);
        reported_humid = activity.findViewById(R.id.reported_humid);


        reported_tempTV.setText(state.get("reported_temperature")); // 온도 넣기
        reported_ledTV.setText(state.get("reported_LED"));
        reported_airCond.setText(state.get("reported_air"));
        reported_humid.setText(state.get("reported_humid"));

        lineChart = (LineChart) activity.findViewById(R.id.chart_temp); // 차트 시각화
        lineChart2 = (LineChart) activity.findViewById(R.id.chart_humid); // 차트 시각화

        List<Entry> temperatures = new ArrayList<>();
        List<Entry> humids = new ArrayList<>();

        String temperatureStr = state.get("reported_temperature");
        int index = cnt++;
        if(index == 5)
            cnt = 0;
        temperatures.add(new Entry(index, 6));
        temperatures.add(new Entry(index+1, 1));
        temperatures.add(new Entry(index+2, 2));
        temperatures.add(new Entry(index+3, 3));
        temperatures.add(new Entry(index+4, 5));

//        temperatures.add(new Entry(2, temperature++));
//        temperatures.add(new Entry(3, temperature++));
//        temperatures.add(new Entry(4, temperature++));
//        temperatures.add(new Entry(5, temperature++));


        humids.add(new Entry(1,6));
        humids.add(new Entry(2,6));
        humids.add(new Entry(3,6));
        humids.add(new Entry(4,6));
        humids.add(new Entry(5,6));
        humids.add(new Entry(6,6));


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

//        int temperature = Integer.parseInt(state.get("reported_temperature"));
//        int humid = Integer.parseInt(state.get("reported_LED"));
//
//
//        if(temperature > 0 && humid > 0 ){
//            dataDto = new DataDto(temperature,humid);
//            dataService = new DataService(dataDto);
//
//        }

        TextView desired_ledTV = activity.findViewById(R.id.desired_led);
        TextView desired_tempTV = activity.findViewById(R.id.desired_temp);
        desired_tempTV.setText(state.get("desired_temperature"));
        desired_ledTV.setText(state.get("desired_LED"));


    }

    @Override
    protected void onProgressUpdate(String... str){  // Main thread 에서 작동 -> 중간중간 UI 업데이트 가능해짐.
//        reported_tempTV.setText(state.get("reported_temperature")); // 실시간 업데이트 테스
//        reported_ledTV.setText(state.get("reported_LED"));

    }

    protected Map<String, String> getStateFromJSONString(String jsonString) {
        Map<String, String> output = new HashMap<>();
        try {
            // 처음 double-quote와 마지막 double-quote 제거
            jsonString = jsonString.substring(1, jsonString.length() - 1);
            // \\\" 를 \"로 치환
            jsonString = jsonString.replace("\\\"", "\"");
            Log.i(TAG, "jsonString=" + jsonString);
            JSONObject root = new JSONObject(jsonString);
            JSONObject state = root.getJSONObject("state");
            JSONObject reported = state.getJSONObject("reported");
            String tempValue = reported.getString("temperature");
            String ledValue = reported.getString("LED");
            String humidValue = reported.getString("humidity");
            String airCondValue = reported.getString("airCondStatus");

            output.put("reported_temperature", tempValue);
            output.put("reported_LED", ledValue);
            output.put("reported_humid",humidValue);
            output.put("reported_air",airCondValue);

            JSONObject desired = state.getJSONObject("desired");
            String desired_tempValue = desired.getString("temperature");
            String desired_ledValue = desired.getString("LED");

            output.put("desired_temperature", desired_tempValue);
            output.put("desired_LED", desired_ledValue);

        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
