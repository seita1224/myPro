package com.example.hiro.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.GoodsReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;
import com.example.hiro.myapplication.maikeView.CardRecyclerView;
import com.example.hiro.myapplication.maikeView.GoodsCardRecyclerAdapter;

import java.util.ArrayList;

public class ConfirmationresultActivity extends AppCompatActivity {

    GoodsCardRecyclerAdapter goodsCardRecyclerAdapter;
    CardRecyclerView cardRecyclerView;

    ConnectionHelper connectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationresult);

        Intent intent = getIntent();
        String goodsName = intent.getStringExtra("goodsName");
        intent = null;

        connectionHelper = new ConnectionHelper(getApplicationContext());

        connectionHelper.setConnectionCallBack(new GoodsReceive() {
            @Override
            public void goodsReceive(ArrayList<Goodsdata> goodsdatas, String connectionStatus) {
                if(goodsdatas != null){
                    cardRecyclerView = new CardRecyclerView(getApplicationContext());
                    cardRecyclerView.setGoodsRecyclerAdapter(getApplicationContext(),goodsdatas);
                    cardRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT));

                    //深見大樹
                    // 例えばベースとしてLinearLayout作成
                    RelativeLayout relativeLayout = new RelativeLayout(getApplication());
                    // 例えばさっき作ったベースにViewを配置
                    Button button = new Button(getApplication());
                    button.setText("一致するプレゼントがない場合はここをクリック!!");
//                    button.setLayoutParams(new RelativeLayout.LayoutParams(
//                            RelativeLayout.LayoutParams.MATCH_PARENT,
//                            RelativeLayout.LayoutParams.WRAP_CONTENT));
                    button.setBackgroundColor(0xffff9393);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(),ProductregistrationActivity.class);
                            startActivity(intent);
                        }
                    });
                    // 目的のビューのレイアウトパラメータを作成。今作った中間ビューの中心に配置
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                    // 目的のビューを中間レイアウトの子にする
                    relativeLayout.addView(button,lp);
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.kei));
                    //setContentView(cardRecyclerView);
                    relativeLayout.addView(cardRecyclerView);
                    // 完成したレイアウトをxmlでのレイアウトの時でも馴染み深い`setContentView()`で画面に出す
                    setContentView(relativeLayout);
                    //深見大樹


                }else{
//                    new AlertDialog.Builder(getApplicationContext())
//                            .setTitle("title")
//                            .setMessage("message")
//                            .setPositiveButton("OK", null)
//                            .show();
                }
            }
        });
        connectionHelper.receiveGoods(goodsName);
    }
}

