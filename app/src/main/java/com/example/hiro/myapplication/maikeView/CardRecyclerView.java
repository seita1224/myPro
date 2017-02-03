package com.example.hiro.myapplication.maikeView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;


import com.example.hiro.myapplication.DBController.Goodsdata;

import java.util.ArrayList;

/**
 * Created by yuki on 2016/05/26.
 */
public class CardRecyclerView extends RecyclerView {
    public CardRecyclerView(Context context){
        super(context);
    }
    public CardRecyclerView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    //ランキング用ArrayList
    ArrayList<Goodsdata> goodsList = null;

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
    }

    //アダプターのセット用メソッド
    public void setRankingRecyclerAdapter(Context context,ArrayList<Goodsdata> goodsdatas){
        //カードビューの生成についてのログ
        Log.d("CardRecyclerView","ランキングカードビューの生成");
        Log.d("CardRecyclerView", String.valueOf(goodsdatas.size()));
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(new RankingCardRecyclerAdapter(context,goodsdatas));
    }

    public void setGoodsRecyclerAdapter(Context context,ArrayList<Goodsdata> goodsdatas){
        //カードビューの生成についてのログ
        Log.d("CardRecyclerView","ランキングカードビューの生成");
        Log.d("CardRecyclerView", String.valueOf(goodsdatas.size()));
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(new GoodsCardRecyclerAdapter(context,goodsdatas));
    }
}
