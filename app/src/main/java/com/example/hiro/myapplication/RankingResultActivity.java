package com.example.hiro.myapplication;

/*
ランキング結果表示
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.RankingReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;
import com.example.hiro.myapplication.maikeView.CardRecyclerView;

import java.util.ArrayList;

public class RankingResultActivity extends AppCompatActivity {

    CardRecyclerView cardRecyclerView = null;
    LinearLayout layout = null;

    ConnectionHelper connectionHelper = null;

    ArrayList<Goodsdata> goodsList = null;

    String age,sex,scene,genre,hobbie,goodstype;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_result);

        Log.d("RankingResultActivity","Load RankingActivity");

        //ランキングアクティビティのレイアウトを保存
        layout = new LinearLayout(this);

        //ランキング選択用変数取得
        intent = getIntent();
        sex = intent.getStringExtra("sex");
        age = intent.getStringExtra("age");
        scene = intent.getStringExtra("scene");
        genre = intent.getStringExtra("genre");
        hobbie = intent.getStringExtra("hobbie");
        goodstype = intent.getStringExtra("goodstype");

//        sex = "1";
//        age = "3";
//        goodstype = "1";

        Log.d("test",sex + age + scene);

        //カードレイアウトの準備
        cardRecyclerView = new CardRecyclerView(getApplicationContext());

        //サーバからデータのダウンロードの準備
        connectionHelper = new ConnectionHelper(getApplicationContext());


        //ランキングデータ受信
        connectionHelper.reciveRanking(age,sex,scene,genre,hobbie,goodstype);

        //データのダウンロード&ArrayListに格納&カードビューにセットの処理を
        connectionHelper.setConnectionCallBack(new RankingReceive() {
            @Override
            public void rankReceive(ArrayList<Goodsdata> goodsdatas,String connectionStatus){
                Log.d("RankingResultActivity","Load CardView");
                cardRecyclerView.setRankingRecyclerAdapter(getApplicationContext(),goodsdatas);
                cardRecyclerView.setBackgroundResource(R.drawable.kei);
                setContentView(cardRecyclerView);
            }
        });
    }
}
