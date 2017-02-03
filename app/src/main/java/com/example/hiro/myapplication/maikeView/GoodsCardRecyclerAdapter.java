package com.example.hiro.myapplication.maikeView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ProductreviewActivity;
import com.example.hiro.myapplication.R;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.ImageReceive;
import com.example.hiro.myapplication.ServerConnectionController.ReceiveImageAsyncTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.bitmap;
import static java.lang.System.out;


/**
 * Created by yuki on 2016/05/26.
 */
public class GoodsCardRecyclerAdapter extends RecyclerView.Adapter<GoodsCardRecyclerAdapter.ViewHolder>{
        private String[] goodsList; //商品名リスト
        private String[] genresList; //商品のリストに対応するジャンル
        private Bitmap goodsImage; //商品のリストに対応する画像
        private Context context;
        private double rate; //商品の評価

        private String imageName;
        private File imageFile;

        ArrayList<Goodsdata> goodsdatas;//ランキング格納用ArrayList
        int i = 0;

        public GoodsCardRecyclerAdapter(Context context, String[] goodsList, String[] genresList, Bitmap goodsImage) {
                super();
                this.goodsList = goodsList;
                this.genresList = genresList;
                this.goodsImage = goodsImage;
                this.context = context;
        }

        //ランキングコンストラクタ
        public GoodsCardRecyclerAdapter(Context context, ArrayList<Goodsdata> goodsdatas){
                super();
                this.context = context;
                this.goodsdatas = goodsdatas;
                Log.d("CardRecycleViewAdapter","コンストラクタ通過");


        }

        @Override
        public int getItemCount() {
                return goodsdatas.size();
        }

        //ViewHolderにあるViewをカードビューにセットする
        @Override
        public void onBindViewHolder(final ViewHolder vh, final int position) {
                //商品名の設定
                vh.mGoodsTextView.setText(goodsdatas.get(position).getGoods_name());
//                Log.d("CardRecycleViewAdapter",goodsdatas.get(position).getGoods_name());

                //ジャンル名設定
                vh.mGenresTextView.setText(goodsdatas.get(position).getGenre());
//                vh.mGenresTextView.setText("test");

                //商品画像の設定
                if(goodsdatas.get(position).getPicture() == null){
                        ReceiveImageAsyncTask receiveImageAsyncTask;
                        receiveImageAsyncTask = new ReceiveImageAsyncTask(goodsdatas.get(position).getImageUrl());
                        receiveImageAsyncTask.setImageReceive(new ImageReceive() {
                                @Override
                                public void receiveImage(Bitmap bm) {
                                        goodsdatas.get(position).setPicture(bm);
                                        vh.mGoodsImageView.setImageBitmap(goodsdatas.get(position).getPicture());
                                        Log.d(getClass().getName(),"ImageView:setImage(" + position + ")");
                                        imageName = Calendar.getInstance().getTime().toString() + ".jpeg";
                                        imageFile = new File(context.getFilesDir(),imageName);
                                        try {
                                                FileOutputStream out = new FileOutputStream(imageFile);
                                        } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                        receiveImageAsyncTask.execute();
                        receiveImageAsyncTask = null;
                }
                vh.mGoodsImageView.setImageBitmap(goodsdatas.get(position).getPicture());
                Log.d(getClass().getName(),"ImageView:setImage(" + position + ")");

                //カードビュー自体をクリックしたときの処理
                vh.mGoodsResultRootLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.d("LayoutClick", String.valueOf(position) + " Rate ; " + goodsdatas.get(position).getRate());
                        }
                });

                //星の数を商品のレートに初期化
                vh.mRateRatingBar.setRating(((float) goodsdatas.get(position).getRate()));
//                vh.mRateRatingBar.setRating((float) 3);


//                レビューボタンをクリックしたときの処理
                vh.mProductReViewButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent = new Intent(context,ProductreviewActivity.class);  //レビュー画面に飛ばす
                                //Bundleに画像データは入れない入れるとアプリ自体が再起動するため
                                intent.putExtras(makeBundle(vh));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                        }
                });
        }

        @Override
        public GoodsCardRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View v = layoutInflater.inflate(R.layout.layout_recycler_seachgoods, parent, false);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
        }

        //今あるデータのBundleを生成
        public Bundle makeBundle(ViewHolder vh){
                Bundle mBundle = new Bundle();
                mBundle.putString("goodsName", (String) vh.mGoodsTextView.getText());
                mBundle.putString("genres", (String) vh.mGenresTextView.getText());
                mBundle.putInt("rate", (int) vh.mRateRatingBar.getRating());

                Log.d(getClass().getName(),imageFile.toString());
                ///画像をアプリの内部領域に保存
                ((BitmapDrawable)vh.mGoodsImageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 0, out);
                mBundle.putString("Image",imageFile.getAbsolutePath());
                Log.d(getClass().getName(),imageFile.getAbsolutePath());
                return mBundle;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
                TextView mGoodsTextView;
                TextView mGenresTextView;
                LinearLayout mGoodsResultRootLayout;
                ImageView mGoodsImageView;
                RatingBar mRateRatingBar;
                BootstrapButton mProductReViewButton;

                public ViewHolder(View v) {
                        super(v);
                        mGoodsTextView = (TextView)v.findViewById(R.id.goodsTextView_goods);
                        mGenresTextView = (TextView)v.findViewById(R.id.genresTextView_goods);
                        mGoodsResultRootLayout = (LinearLayout)v.findViewById(R.id.goodsResultrootLayout);
                        mGoodsImageView = (ImageView)v.findViewById(R.id.goodsImageView_goods);
                        mRateRatingBar = (RatingBar)v.findViewById(R.id.rateRaitingBar_goods);
                        mProductReViewButton = (BootstrapButton)v.findViewById(R.id.productReviewButton_goods);
                }
        }

}
