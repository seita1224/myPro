package com.example.hiro.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.RankingReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;
import com.example.hiro.myapplication.maikeView.CardRecyclerView;

import java.util.ArrayList;

public class ConfirmationresultNotActivity extends AppCompatActivity {

    String data1;
    ImageView imageView;
    TextView goodsname;

    ConnectionHelper connectionHelper = null;

    ArrayList<Goodsdata> goodsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationresult);

        Intent intent = getIntent();
        data1 = intent.getStringExtra("DATA1");
        goodsname = (TextView)findViewById(R.id.goodsname);
//        textView = (TextView) findViewById(R.id.goodsname);
//        textView.setText(data1);


















        //////////////////////////////////
        Log.d("RankingResultActivity","Load RankingActivity");

//        sex = "1";
//        age = "3";
//        goodstype = "1";


        //サーバからデータのダウンロードの準備
        connectionHelper = new ConnectionHelper(getApplicationContext());


        //ランキングデータ受信
        connectionHelper.receiveGoods(data1);

        //データのダウンロード&ArrayListに格納&カードビューにセットの処理を
        connectionHelper.setConnectionCallBack(new RankingReceive() {
            @Override
            public void rankReceive(ArrayList<Goodsdata> goodsdatas, String connectionStatus){
                Log.d("RankingResultActivity","Load CardView");
                goodsname.setText(goodsdatas.get(0).getGoods_name());
                Log.d("aaaaaaaaaaaaaaaaaaaaa",goodsdatas.get(0).getGoods_name());
                imageView = (ImageView)findViewById(R.id.goodsImageView);
                imageView.setImageBitmap(goodsdatas.get(0).getPicture());
            }
        });
        ////////////////////////////















        CardView card = (CardView) findViewById(R.id.cardView);
        card.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),ReviewActivity.class);
                intent.putExtra("DATA1",data1);
                startActivity(intent);
            }
        });
    }
}
