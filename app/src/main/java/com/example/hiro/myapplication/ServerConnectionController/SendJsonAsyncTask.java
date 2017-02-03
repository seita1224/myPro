package com.example.hiro.myapplication.ServerConnectionController;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.SendCallBack;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by seita on 2016/10/21.
 */

public class SendJsonAsyncTask extends AsyncTask<String,Void,String> {
    //フィールド
    private JSONObject jsonObject = null;
    private JSONArray jsonArray = null;
    private URL url = null;
    private String request;
    private SendCallBack sendCallBack;
    int flg;

    //---------------------------------コンスラクタ---------------------------------
    public SendJsonAsyncTask(){}

    public SendJsonAsyncTask(URL url){
        this.url = url;
    }

    public SendJsonAsyncTask(URL url, JSONObject json){
        this.url = url;
        this.jsonObject = json;
        flg = 0;
        Log.d(getClass().getName(),json.toString());
    }

    public SendJsonAsyncTask(URL url, JSONArray json){
        this.url = url;
        this.jsonArray = json;
        flg = 1;
        Log.d(getClass().getName(),json.toString());
    }

    public SendJsonAsyncTask(URL url, String request){
        this.url = url;
        this.request = request;
        flg = 2;
        Log.d(getClass().getName(),request);
    }

    //------------------------------------------------------------------------------


    //非同期処理(文字列送信してる)
    @Override
    protected String doInBackground(String... str) {
        HttpURLConnection httpc = null; //Http通信用コネクター
        OutputStream out = null;    //出力用OutPutStream
        String message = null;

        try {
            InputStream is = null;
            //POST送信用プロパティの設定
            httpc = (HttpURLConnection)url.openConnection();
            httpc.setDoOutput(true);
            httpc.setRequestMethod("POST");
            httpc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpc.setChunkedStreamingMode(0);

            //出力用のOutputStreamの生成
            out = httpc.getOutputStream();
            //POST送信処理

            //送信データの登録
            out.write(request.getBytes());

            //送信
            out.flush();
            Log.d(getClass().getName(),out.toString());

            message = httpc.getResponseMessage();

            //もしOutputStreamオブジェクトがある場合クローズ
            if(out != null){
                out.close();
            }
            if(httpc.getInputStream() != null){
                is = httpc.getInputStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            message = sb.toString();
        } catch (IOException e) {
            Log.e("error",e.toString());
            Log.e(getClass().getName(),httpc.getRequestMethod());
        }finally {
            //接続破棄
            httpc.disconnect();
        }
        Log.d(getClass().getName(),message);
        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);

        sendCallBack.SendCallBack(message);
    }

    public void setSendCallBack(SendCallBack sc){
        sendCallBack = sc;
    }

}
