package com.example.android_resapi.ui;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class DeviceActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Button startGetBtn;
    Button stopGetBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingShadowURL");

        startGetBtn = findViewById(R.id.startGetBtn);
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

        TextView desired_ledTV = findViewById(R.id.desired_led);
        TextView desired_tempTV = findViewById(R.id.desired_temp);
        desired_tempTV.setText("");
        desired_ledTV.setText("");
    }

}


