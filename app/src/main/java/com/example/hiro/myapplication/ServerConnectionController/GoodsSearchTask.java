package com.example.hiro.myapplication.ServerConnectionController;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.AsyncCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.AsyncCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

;

/**
 * Created by seita on 2016/10/20.
 */

public class GoodsSearchTask extends AsyncTask<Void,Void,JSONObject> {
    private URL url;    //接続するURL
    private StringBuilder result = new StringBuilder();
    private AsyncCallBack asyncCallBack;
    private String connectionStatus;
    private int statusCode;

    //---------------------------------コンスラクタ---------------------------------

//    public ReceiveJsonAsyncTask(){  }

    public GoodsSearchTask(URL url) {
        this.url = url;
    }

    //------------------------------------------------------------------------------

    //非同期処理(メソッド)
    @Override
    protected JSONObject doInBackground(Void... params) {
        HttpURLConnection httpc = null; //http通信コネクター
        JSONObject jo = null;
        //URLがセットされていない場合のエラーコードを表示
        if(url == null){
            connectionStatus = ("URLが設定されていません");
            statusCode = 002;

            try {
                jo = new JSONObject(connectionStatus);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jo;
        }

        try {
            //指定されたURLにコネクション開始
            httpc = (HttpURLConnection)url.openConnection();


            Log.d("aaaaaaaaaaaaaaaaaaaaaa",url.toString());



            httpc.connect();
        } catch (MalformedURLException e) {
            Log.e("ReceiveJsonAsncTask1",e.toString());
        } catch (IOException e) {
            Log.e("ReceiveJsonAsncTask2",e.toString());
        }
        try {
            //コネクションの結果を格納
            int status = httpc.getResponseCode();
            //コネクションに成功した時
            if(status == HttpURLConnection.HTTP_OK){
                //コネクターから取得データの取り出し
                InputStreamReader IReader = new InputStreamReader(httpc.getInputStream());
                BufferedReader BReader = new BufferedReader(IReader);

                String line = null;
                //取得データの変換(1行ずつStringBuilderに格納)
                while((line = BReader.readLine()) != null) {
                    result.append(line);
                    Log.d("ReceiveJsonAsncTask3",line);
                }

                Log.e("Json前のデータは？",result.toString());
                String henkan = result.toString();
                henkan = henkan.substring(1);
                henkan = henkan.substring(0,henkan.length()-1) ;
                Log.e("json変換",henkan);

                //取得データをJsonに変換
                jo = new JSONObject(henkan);
                Log.e("Json後のデータは？",jo.toString());


                //読み込み作業終了
                IReader.close();
                BReader.close();
            }
        }catch (MalformedURLException e){
            Log.e("error",e.toString());
        } catch (IOException e) {
            Log.e("error",e.toString());
        } catch (JSONException e) {
            Log.e("error",e.toString());
        } finally {
            //接続破棄
            httpc.disconnect();
        }
        //JSONObjectが作成されていない場合エラーを返す
        if(jo == null){
            connectionStatus = "JSONObjectの生成に失敗しました。";
            statusCode = 003;
        }else{
            connectionStatus = "通信に成功しました";
            statusCode = 001;
        }
        return jo;
    }

    @Override
    protected void onPostExecute(JSONObject jo) {
        super.onPostExecute(jo);
        //コールバック先呼出
        asyncCallBack.asyncCallBack(jo);
    }


    //通信状況確認用コードの取得用メソッド
    public int getStatusCode(){return statusCode;}
    public String getConnectionStatus(){return connectionStatus;}

    //コールバック処理セットメソッド
    public void setCallBack(AsyncCallBack cb){
        asyncCallBack = cb;
    }
}



