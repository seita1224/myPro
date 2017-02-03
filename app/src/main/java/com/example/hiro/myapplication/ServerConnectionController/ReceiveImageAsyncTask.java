package com.example.hiro.myapplication.ServerConnectionController;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;


import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.ImageReceive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by seita on 2016/12/14.
 */

public class ReceiveImageAsyncTask extends AsyncTask <Void,Void,Bitmap>{

    URL url = null;
    ImageReceive imageReceive;

    public ReceiveImageAsyncTask(){}
    public ReceiveImageAsyncTask(URL url){
        this.url = url;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        Bitmap bitmap = null;
        InputStream is = null;
        try {
            //URLからストリームの読み込み
            is = url.openStream();

            //フィールドの画像にセットする
            bitmap = BitmapFactory.decodeStream(is);

            //ストリームのクローズ
            is.close();
        } catch (IOException e) {
            Log.d("ReceiveImage","画像の取得に失敗しました");
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageReceive.receiveImage(bitmap);
    }


    public void setImageReceive(ImageReceive ir){
        this.imageReceive = ir;
    }

}
