package com.example.android_resapi.httpconnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/*
* AsyncTask는 ui 스레드와는 별도로 동작하는 스레드에서 작동하게 해줌
* 보통 안드로이드 앱 코드를 보면 UI와 관련된 코드(액티비티에서 구현함 거의)  , network와 관련된 코드로 이루어져 있음.
* 앱상에서 버튼을 눌려서 network operation 을 호출하고 aws iot 에 있는 플랫폼과 통신을 하게됨. (HTTP 통신) */
abstract public class GetRequest extends AsyncTask<String, Void, String> { // AsyncTask의 역할을 알고 있어야함.
    final static String TAG = "AndroidAPITest";
    protected Activity activity;
    protected URL url;

    public GetRequest(Activity activity) {
        this.activity = activity;
    }


    /* 실제 network operation 코드를 작성하는 곳. <-> Ui 스레드에서 동작하는 코드 : GetThings
    *  GetThings가 network operation을 하는 AsyncTask를 호출할 때 ui 스레드에서 network operation 코드로 어떠한 정보(url ? ) 를 넘겨주려고 할 때 "onPreExecute() " 를 통해서 넘겨줌
    *  그 다음 doInBackground 코드가 실행됨.
    *  network operation의 실행결과를 ui로 넘겨줄 때는 onPostExecute() 를 통해서 넘겨줌
    * *** AsyncTask 는 onPreExecute() , doInBackground() , onPostExecute()  이렇게 3개의 메소드를 override(재정의)해서 사용함  **** */
    @Override
    protected String doInBackground(String... strings) {
        StringBuffer output = new StringBuffer();

        try {
            if (url == null) {
                Log.e(TAG, "Error: URL is null ");
                return null;
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 서버랑 실제 connection 하는 부

            if (conn == null) {
                Log.e(TAG, "HttpsURLConnection Error");
                return null;
            }
            conn.setConnectTimeout(10000); // 10초 기다리기 아니면 timeout
            conn.setRequestMethod("GET"); // get 형식
            conn.setDoInput(true); // input 은 있음
            conn.setDoOutput(false); // output 은 없음

            int resCode = conn.getResponseCode(); // 서버로부터의 응답

            if (resCode != HttpsURLConnection.HTTP_OK) {
                Log.e(TAG, "HttpsURLConnection ResponseCode: " + resCode);
                conn.disconnect();
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while (true) {
                line = reader.readLine(); // 한줄씩 읽기
                if (line == null) {
                    break;
                }
                output.append(line);  // 스트링 버퍼에 한줄씩 추가
            }

            reader.close();
            conn.disconnect();

        } catch (IOException ex) {
            Log.e(TAG, "Exception in processing response.", ex);
            ex.printStackTrace();
        }

        return output.toString(); // 스트링 버퍼에 쌓인 문자열 return -> GET 요청으로 인해 생긴 서버가 응답한 결 -> GetThings의 onPostExecute(String jsonString)으로 감.
    }

}
