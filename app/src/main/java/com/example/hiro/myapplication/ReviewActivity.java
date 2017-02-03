package com.example.hiro.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.RankingReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    ConnectionHelper connectionHelper;
    TextView goodsname;
    ImageView imageView;
    EditText editText;
    Button button;
    InputMethodManager inputMethodManager;
    RelativeLayout mainLayout;

    Bitmap goodsImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);

        Intent intent = getIntent();
        String data1 = intent.getStringExtra("DATA1");
        //グッズデータとかその他もろもろ
        //////////////////////////////////
        goodsname = (TextView)findViewById(R.id.goodsname);
        //...の処理
        goodsname.setHorizontallyScrolling(true);
        goodsname.setEllipsize(TextUtils.TruncateAt.END);
        imageView = (ImageView)findViewById(R.id.goodsImageView);
        Log.d("RankingResultActivity","Load RankingActivity");
        //サーバからデータのダウンロードの準備
        connectionHelper = new ConnectionHelper(getApplicationContext());
        //ランキングデータ受信
        connectionHelper.receive(data1);
        //データのダウンロード&ArrayListに格納&カードビューにセットの処理を
        connectionHelper.setConnectionCallBack(new RankingReceive() {
            @Override
            public void rankReceive(ArrayList<Goodsdata> goodsdatas, String connectionStatus){
                Log.d("RankingResultActivity","Load CardView");
                goodsname.setText(goodsdatas.get(0).getGoods_name());
                Log.d("aaaaaaaaaaaaaaaaaaaaa",goodsdatas.get(0).getGoods_name());
                imageView.setImageBitmap(goodsdatas.get(0).getPicture());

                //エディットテキストにヒントを追加
                editText = (EditText)findViewById(R.id.reviewText);
                editText.setHint(goodsdatas.get(0).getGoods_name()+"のレビューを入力してください。");
            }
        });
        ////////////////////////////

//        Resources r = getResources();
//        Bitmap bm = BitmapFactory.decodeResource(r,R.drawable.robot);
//        imageView.setImageBitmap(bm);

        //ボタンクリック時の処理
        //////////////////////////////
        button = (Button)findViewById(R.id.pregiButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //背景タップ時にキーボードを閉じる！！！！！
        ///////////////////
        //キーボードを閉じたいEditTextオブジェクト
        editText = (EditText) findViewById(R.id.editText);
        //画面全体のレイアウト
        mainLayout = (RelativeLayout)findViewById(R.id.ReviewActivity);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();
        return false;
    }


}