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
public class RankingJsonPase {
    //フィールド
    private JSONObject rootJsonObject = null;
    private JSONArray rootJsonArray = null;

    //コンストラクタ
    //デフォルトコンストラクタ
    public RankingJsonPase(){}

    //JSONObject
    public RankingJsonPase(JSONObject jo){this.rootJsonObject = jo;}

    //JSONArray
    public RankingJsonPase(JSONArray ja){this.rootJsonArray = ja;}

    //JSONのデータを判別するためのメソッド
    public boolean serectInfo() {
        Log.d("RankingJsonParse","serectInfo");
        try {
            //ここにデータ種別が増えたときcase文追加
           if("Ranking" == rootJsonObject.getString("Type")){
               return true;
           }else{
               return false;
           }
        } catch (JSONException e) {
            Log.e("RankingJsonPase", e.toString());
        }
        return false;
    }


    //ランキングの文字列を返すメソッド
    public ArrayList<Goodsdata> getRanking(){
        //ランキング格納用ArrayList
        ArrayList<Goodsdata> rankList = new ArrayList<>();

        //一時退避用JSONObject,JSONArray
        JSONArray tempJa;
        JSONObject tempJo;

        try {

            //取得するデータがあっているかどうか判定
            serectInfo();

            //JSONArrayにランキング配列を入れる
            tempJa = rootJsonObject.getJSONArray("Items");

            //JSONから順位だけを抜き出す
            for (int i = 0;i < 20;i++){
                tempJo = tempJa.getJSONObject(i).getJSONObject("Item");

                //データの確認
                Log.d("JsonParse", tempJa.getJSONObject(i).getJSONObject("Item").toString());

                //GoodsdataClassに一つずつ入れる
                Goodsdata data = new Goodsdata();

                //データを一つずつ格納する
                data.setRanking_no(tempJo.getInt("ranking_no"));
                data.setGoods_id(tempJo.getInt("getgoods_id"));
                data.setGoods_name(tempJo.getString("name"));
                data.setGenre(tempJo.getString("genres"));
                data.setRate(tempJo.getDouble("average_rate"));

                try {
                    data.setImage(new URL(tempJo.getString("image")));
                } catch (MalformedURLException e) {
                    Log.d("JsonParse","URLの変換に失敗しました");
                }

                //GoodsdataをrankListに追加
                rankList.add(data);
            }
        } catch (JSONException e) {
            Log.e("RankingJsonPase","ランキングデータの生成に失敗しました : " + e.toString());
        }
        return rankList;
    }

    //JSONObjectのgetter,setter
    public JSONObject getJo() {return rootJsonObject;}
    public void setJo(JSONObject jo) {this.rootJsonObject = jo;}

    //JSONArrayのgetter,setter
    public JSONArray getJa(){return rootJsonArray;}
    public void setJa(JSONArray ja){this.rootJsonArray= ja;}
}
