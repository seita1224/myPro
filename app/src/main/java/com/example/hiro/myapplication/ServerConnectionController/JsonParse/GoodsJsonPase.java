package com.example.hiro.myapplication.ServerConnectionController.JsonParse;

import android.util.Log;

import com.example.hiro.myapplication.DBController.Goodsdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by seita on 2016/10/24.
 */

//Jsonデータをパース(解析)するクラス
public class GoodsJsonPase {
    //フィールド
    private JSONObject rootJsonObject = null;
    private JSONArray rootJsonArray = null;

    //コンストラクタ
    //デフォルトコンストラクタ
    public GoodsJsonPase(){}

    //JSONObject
    public GoodsJsonPase(JSONObject jo){this.rootJsonObject = jo;}

    //JSONArray
    public GoodsJsonPase(JSONArray ja){this.rootJsonArray = ja;}

    //JSONのデータを判別するためのメソッド
    public boolean serectInfo() {
        Log.d("GoodsJsonPase","serectInfo");
        try {
            //ここにデータ種別が増えたときcase文追加
           if("SerchResult" == rootJsonObject.getString("Type")){
               return true;
           }else{
               return false;
           }
        } catch (JSONException e) {
            Log.e("GoodsJsonPase", e.toString());
        }
        return false;
    }


    //ランキングの文字列を返すメソッド
    public ArrayList<Goodsdata> getGoods(){
        //ランキング格納用ArrayList
        ArrayList<Goodsdata> goodsList = new ArrayList<>();

        //一時退避用JSONObject,JSONArray
        JSONArray tempJa;
        JSONObject tempJo;

        try {

            //取得するデータがあっているかどうか判定
            serectInfo();

            //JSONArrayにランキング配列を入れる
            tempJa = rootJsonObject.getJSONArray("Items");

            if(tempJa.length() == 0){
                return null;
            }

            //JSONから順位だけを抜き出す
            for (int i = 0;i < tempJa.length();i++){
                tempJo = tempJa.getJSONObject(i).getJSONObject("Item");

                //データの確認
                Log.d("GoodsJsonPase","JSON : " + tempJa.getJSONObject(i).getJSONObject("Item").toString());

                //GoodsdataClassに一つずつ入れる
                Goodsdata data = new Goodsdata();

                //データを一つずつ格納する;
                data.setGoods_id(tempJo.getInt("id"));
                data.setGoods_name(tempJo.getString("name"));

                try {
                    data.setImage(new URL(tempJo.getString("image")));
                } catch (MalformedURLException e) {
                    Log.d("JsonParse","画像URLの変換に失敗しました");
                }

                //GoodsdataをrankListに追加
                goodsList.add(data);
            }
        } catch (JSONException e) {
            Log.e("GoodsJsonPase","商品データの生成に失敗しました : " + e.toString());
        }

        Log.d("GoodsJsonPase","JSON : " + "ArrayList : " + goodsList.get(0).getGoods_name());

        return goodsList;
    }

    //JSONObjectのgetter,setter
    public JSONObject getJo() {return rootJsonObject;}
    public void setJo(JSONObject jo) {this.rootJsonObject = jo;}

    //JSONArrayのgetter,setter
    public JSONArray getJa(){return rootJsonArray;}
    public void setJa(JSONArray ja){this.rootJsonArray= ja;}
}
