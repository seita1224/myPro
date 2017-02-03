package com.example.hiro.myapplication.ServerConnectionController;

import android.content.Context;
import android.util.Log;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.DBController.Userdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.AsyncCallBack;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.SendCallBack;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.GoodsReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.GoodsRegister;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.RankingReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.UserReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.UserSend;
import com.example.hiro.myapplication.ServerConnectionController.JsonParse.GoodsJsonPase;
import com.example.hiro.myapplication.ServerConnectionController.JsonParse.GoodsSearch;
import com.example.hiro.myapplication.ServerConnectionController.JsonParse.RankingJsonPase;
import com.example.hiro.myapplication.ServerConnectionController.JsonParse.receiveGoods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by seita on 2016/10/31.
 */

//データを受信、送信できるよう処理をする中間クラス
public class ConnectionHelper {
    private SendJsonAsyncTask send = null;  //データ送信用の非同期処理クラス
    private ReceiveJsonAsyncTask receive = null;    //データ受信用の非同期処理クラス
    private GoodsSearchTask goods = null;//goods用データ受信用の非同期処理クラス

    private URL url = null; //送受信先のURL
    private RankingJsonPase rankingJsonPase;
    private String connectionStatus;
    private int statusCode;
    private Context context;

    //送信用JSON
    JSONObject userJson = null;

    //URL生成用
    final private String DOMEIN = "http://choome.itsemi.net/";
    final private String API_VERSION = "api/1.0/";
    final private String API_KEY = "&key=pcdEhBroxNohtmKoek8iE34hQ6FZYbp";

    //POSTデータ送信用
    final String NAME = "name=";
    final String EMAIL = "email=";
    final String AGE = "age=";
    final String PASSWORD = "password=";
    final String SEX = "sex=";
    final String HOBBIES_ID = "hobbies_id=";
    final String COMMENT = "comment=";
    final String RATE = "rate=";
    final String GOODSTYPE = "goodstype=";
    final String SCENES = "scenes=";
    final String GENRES = "genres=";
    final String TOKEN = "token=";

    //ランキングに対応するArrayList
    ArrayList<Goodsdata> goodsdatas;

    //コールバック用変数
    private RankingReceive rankingReceive;
    private UserReceive userReceive;
    private GoodsReceive goodsReceive;
    private UserSend userSend;
    private GoodsRegister goodsRegister;

    //-----------------------------コンストラクタ-----------------------------
    public ConnectionHelper(Context context){
        this.context = context;
    }
    
    
    //-----------------------------受信-----------------------------
    //ユーザ情報受信
    public void receiveUserTask(){
        receive = new ReceiveJsonAsyncTask(url);
        receive.setCallBack(new AsyncCallBack() {
            @Override
            public void asyncCallBack(JSONObject jo) {
                userReceive.receiveUser();
                Log.d("ConnectionHelper","CallBack");
            }

            @Override
            public void asyncCallBack(JSONArray ja) {

            }
        });
        receive.execute();
    }

    //ランキング情報の受信
    public void reciveRanking(String age,String sex,String scene,String genre,String hobbie,String goodstype){
        Log.d("ConnectionHelper","reciveRanking_");

        setUrl("ranking/?",age,sex,scene,genre,hobbie,goodstype);

        receive = new ReceiveJsonAsyncTask(url);
        receive.setCallBack(new AsyncCallBack() {
            @Override
            public void asyncCallBack(JSONObject jo) {
                if (checkError()) {
                    RankingJsonPase jp = new RankingJsonPase(jo);
                    goodsdatas = jp.getRanking();
                    rankingReceive.rankReceive(goodsdatas, connectionStatus);
                }
            }
            @Override
            public void asyncCallBack(JSONArray ja) {}
        });
        receive.execute();
        Log.d("ConnectionHelper","通信処理");
    }

    //商品検索結果
    public void receiveGoods(String word){
        setUrl("serchresult/?" , word);
        Log.d("ConnectionHelper","serchresult");
        receive = new ReceiveJsonAsyncTask(url);
        receive.setCallBack(new AsyncCallBack() {
            @Override
            public void asyncCallBack(JSONObject jo) {
                if (checkError()) {
                    GoodsJsonPase jp = new GoodsJsonPase(jo);
                    goodsdatas = jp.getGoods();
                    goodsReceive.goodsReceive(goodsdatas,connectionStatus);
                }
            }

            @Override
            public void asyncCallBack(JSONArray ja) {

            }
        });
        receive.execute();
    }

    //商品予測検索情報の受信
    public void reciveGoodsSearch(String word){
        Log.d("ConnectionHelper","GoodsSearch!!!!_");

        setUrl("preserch/?",word);

        receive = new ReceiveJsonAsyncTask(url);
        receive.setCallBack(new AsyncCallBack() {
            @Override
            public void asyncCallBack(JSONObject jo) {
            }

            @Override
            public void asyncCallBack(JSONArray ja) {
                if (checkError()) {
                    GoodsSearch jp = new GoodsSearch(ja);
                    goodsdatas = jp.getRanking();
                    rankingReceive.rankReceive(goodsdatas, connectionStatus);
                }
            }
        });

        receive.execute();
        Log.d("ConnectionHelper","通信処理");
    }

    //レビュー情報の受信
    public void receive(String word){
        setUrl("serchresult/?" , word);
        Log.d("ConnectionHelper","serchresult");
        receive = new ReceiveJsonAsyncTask(url);
        receive.setCallBack(new AsyncCallBack() {
            @Override
            public void asyncCallBack(JSONObject jo) {
                if (checkError()) {
                    receiveGoods jp = new receiveGoods(jo);
                    goodsdatas = jp.getRanking();
                    rankingReceive.rankReceive(goodsdatas,connectionStatus);
                }
            }

            @Override
            public void asyncCallBack(JSONArray ja) {

            }
        });
        receive.execute();
    }

    //-----------------------------送信-----------------------------
    //ユーザ登録
    public void sendRegistrationUser(Userdata userdata){
        setUrl("apiregister/?");
        //ユーザーデータPOSTデータ化
        String request = "";
        request += NAME + userdata.getName() + "&";
        request += EMAIL + userdata.getEmail() + "&";
        request += PASSWORD + userdata.getPassword() + "&";
        request += SEX + userdata.getSex() + "&";
        request += AGE + userdata.getAge() + "&";
        request += HOBBIES_ID + userdata.getHobbies();
        request += API_KEY;

        //Userのデータを非同期で送信する
        send = new SendJsonAsyncTask(url,request);
        send.setSendCallBack(new SendCallBack() {
            @Override
            public void SendCallBack(String message) {
                userSend.responseUserMessage(message);
            }
        });
        send.execute();
    }

    //ログイン
    public void sendLoginUser(Userdata userdata){
        setUrl("gettoken/?");

        String request = EMAIL + userdata.getEmail();
        request += PASSWORD + userdata.getPassword();
        request += API_KEY;

        send = new SendJsonAsyncTask(url,request);
        send.setSendCallBack(new SendCallBack() {
            @Override
            public void SendCallBack(String message) {
                userSend.responseUserMessage(message);
            }
        });
        send.execute();
    }

    //商品登録
    public void sendGoodsdata(Goodsdata goodsdata, String goodsType){
        setUrl("register-and-review/?");

        String request = NAME + goodsdata.getGoods_name() + "&";
        request += COMMENT + goodsdata.getComment() + "&";
        request += RATE + goodsdata.getRate() + "&";
        request += GENRES + goodsdata.getGenre() + "&";
        request += SCENES + goodsdata.getScene() + "&";
        request += GOODSTYPE + goodsType;
        request += API_KEY + "&" + TOKEN + ((Userdata)context).getToken();

        send = new SendJsonAsyncTask(url,request);
        send.setSendCallBack(new SendCallBack() {
            @Override
            public void SendCallBack(String message) {

            }
        });
        send.execute();
    }

    //送受信用URLにURLをセット
    public void setUrl(String informationType,String age,String sex,String scene,String genre,String hobbie,String goodstype){
        try {
            url = new URL(DOMEIN +
                    API_VERSION +
                    informationType +
                    "age=" + age +
                    "&sex=" + sex +
                    "&scene=" + scene +
                    "&genre=" + genre +
                    "&hobbie=" + hobbie +
                    "&goodstype=" + goodstype+
                    API_KEY);

            Log.d("URL",DOMEIN +
                    API_VERSION +
                    informationType +
                    "age=" + age +
                    "&sex=" + sex +
                    "&scene=" + scene +
                    "&genre=" + genre +
                    "&hobbie=" + hobbie +
                    "&goodstype=" + goodstype+
                    API_KEY);
        } catch (MalformedURLException e) {
            Log.e("ConnectionHelper",e.toString());
        }
    }

    //送受信用URLにURLをセット(商品検索用URL)
    public void setUrl(String dataTyep,String word){
        try {
            url = new URL((DOMEIN +
                    API_VERSION +
                    dataTyep +
                    "word=" + word +
                    API_KEY));
            Log.d(getClass().getName(),",URL:" + url.toString());
        } catch (MalformedURLException e) {
            Log.e("ConnectionHelper",e.toString());
        }
    }

    //送信用URLにURLをセット
    public void setUrl(String str){
        try {
            url = new URL((DOMEIN +
                    API_VERSION +
                    str +
                    API_KEY));
            Log.d(getClass().getName(),",URL:" + url.toString());
        } catch (MalformedURLException e) {
            Log.e("ConnectionHelper",e.toString());
        }
    }

    //コールバック処理セットメソッド
    //ランキング
    public void setConnectionCallBack(RankingReceive rankingReceive){this.rankingReceive = rankingReceive;}
    //商品検索結果
    public void setConnectionCallBack(GoodsReceive goodsReceive){this.goodsReceive = goodsReceive;}
    //ユーザー
    public void setConnectionCallBack(UserReceive userReceive){this.userReceive = userReceive;}

    //ユーザー登録用
    public  void setConnectionCallBack(UserSend userSend){this.userSend = userSend;}

    //エラー処理用メソッド
    public boolean checkError(){
        statusCode = receive.getStatusCode();
        //エラーコードが設定されている場合
        switch (receive.getStatusCode()){
            case 001:
                connectionStatus = receive.getConnectionStatus();
                Log.d("ConnectionHelper",connectionStatus);
                break;
            case 002:
                connectionStatus = receive.getConnectionStatus();
                Log.e("ConnectionHelper",connectionStatus);
                return false;
            case 003:
                connectionStatus = receive.getConnectionStatus();
                Log.e("ConnectionHelper",connectionStatus);
                return false;
        }
        return true;
    }

    public void setUserReceive(UserReceive userReceive) {
        this.userReceive = userReceive;
    }

    public void setUserSend(UserSend userSend) {
        this.userSend = userSend;
    }

    public void setGoodsRegister(GoodsRegister goodsRegister) {
        this.goodsRegister = goodsRegister;
    }
}