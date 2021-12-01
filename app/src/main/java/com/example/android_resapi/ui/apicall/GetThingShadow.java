package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;
import com.example.android_resapi.ui.DeviceActivity;
import com.example.android_resapi.ui.dto.DataDto;
import com.example.android_resapi.ui.service.DataService;

public class GetThingShadow extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    DataDto dataDto;
    DataService dataService;
    public GetThingShadow(Activity activity, String urlStr) { // activity == DeviceActivity
        super(activity);
        this.urlStr = urlStr;
    }

    @Override
    protected void onPreExecute() {
        try {
            Log.e(TAG, urlStr);
            url = new URL(urlStr);

        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            activity.finish();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        Map<String, String> state = getStateFromJSONString(jsonString);
        TextView reported_ledTV = activity.findViewById(R.id.reported_led);
        TextView reported_tempTV = activity.findViewById(R.id.reported_temp);
        reported_tempTV.setText(state.get("reported_temperature")); // 온도 넣기
        reported_ledTV.setText(state.get("reported_LED"));


        int temperature = Integer.parseInt(state.get("reported_temperature"));
        int humid = Integer.parseInt(state.get("reported_LED"));


//        if(temperature > 0 && humid > 0 ){
//            dataDto = new DataDto(temperature,humid);
//            dataService = new DataService(dataDto);
//
//        }

        TextView desired_ledTV = activity.findViewById(R.id.desired_led);
        TextView desired_tempTV = activity.findViewById(R.id.desired_temp);
        desired_tempTV.setText(state.get("desired_temperature"));
        desired_ledTV.setText(state.get("desired_LED"));

        Intent intent = new Intent(activity, DeviceActivity.class );
        intent.putExtra("temperature", temperature);
        intent.putExtra("humid", humid);

        activity.startActivity(intent);

    }

    protected Map<String, String> getStateFromJSONString(String jsonString) {
        Map<String, String> output = new HashMap<>();
        try {
            // 처음 double-quote와 마지막 double-quote 제거
            jsonString = jsonString.substring(1,jsonString.length()-1);
            // \\\" 를 \"로 치환
            jsonString = jsonString.replace("\\\"","\"");
            Log.i(TAG, "jsonString="+jsonString);
            JSONObject root = new JSONObject(jsonString);
            JSONObject state = root.getJSONObject("state");
            JSONObject reported = state.getJSONObject("reported");
            String tempValue = reported.getString("temperature");
            String ledValue = reported.getString("LED");
            output.put("reported_temperature", tempValue);
            output.put("reported_LED",ledValue);

            JSONObject desired = state.getJSONObject("desired");
            String desired_tempValue = desired.getString("temperature");
            String desired_ledValue = desired.getString("LED");
            output.put("desired_temperature", desired_tempValue);
            output.put("desired_LED",desired_ledValue);

        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
