package com.example.hiro.myapplication.Productreview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.hiro.myapplication.R;

/**
 * Created by yuki on 2016/05/26.
 */
public class CardRecyclerView2 extends RecyclerView {
    public CardRecyclerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRecyclerAdapter(context);
    }

    public void setRecyclerAdapter(Context context){
        setLayoutManager(new LinearLayoutManager(context));
        BitmapDrawable bm = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
        setAdapter(new CardRecyclerAdapter(context,context.getResources().getStringArray(R.array.goodsE),context.getResources().getStringArray(R.array.genresE),
                bm.getBitmap()));
    }
}
