package com.example.hiro.myapplication.DBController;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.net.URL;

/**
 * Created by seita_v on 2016/10/21.
 */

//商品情報を扱うクラス
public class Goodsdata implements Parcelable{
    //フィールド
    private int ranking_no;//順位
    private String goods_name;//商品の名前
    private int goods_id;//商品ID
    private double rate;//評価
    private Bitmap picture;//画像
    private URL imageUrl;
    private String comment;//コメント
    private String genre;//ジャンル
    private String scene;//シーン
    private String hobbies;//趣味

    //コンストラクタ
    public Goodsdata(){}

    protected Goodsdata(Parcel in) {
        ranking_no = in.readInt();
        goods_name = in.readString();
        goods_id = in.readInt();
        rate = in.readDouble();
        picture = in.readParcelable(Bitmap.class.getClassLoader());
        comment = in.readString();
        genre = in.readString();
        scene = in.readString();
        hobbies = in.readString();
    }

    public static final Creator<Goodsdata> CREATOR = new Creator<Goodsdata>() {
        @Override
        public Goodsdata createFromParcel(Parcel in) {
            return new Goodsdata(in);
        }

        @Override
        public Goodsdata[] newArray(int size) {
            return new Goodsdata[size];
        }
    };

    //ranking_no
    public int getRanking_no() {return ranking_no;}
    public void setRanking_no(int ranking_no) {this.ranking_no = ranking_no;}

    //goods_name
    public String getGoods_name() {return goods_name;}
    public void setGoods_name(String goods_name) {this.goods_name = goods_name;}

    //goods_id
    public int getGoods_id() {return goods_id;}
    public void setGoods_id(int goods_id) {this.goods_id = goods_id;}

    //rate
    public double getRate() {return rate;}
    public void setRate(double rate) {this.rate = rate;}

    //picture
    public Bitmap getPicture() {return picture;}
    public void setPicture(Bitmap picture) {this.picture = picture;}

    //comment
    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}

    //genre
    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    //scene
    public String getScene() {return scene;}
    public void setScene(String scene) {this.scene = scene;}

    //getHobby
    public String getHobbies() {return hobbies;}
    public void setHobbies(String hobbies) {this.hobbies = hobbies;}

    //イメージファイルのURLをセットしダウンロードしてくる
    public void setImage(URL url){
        Log.d("Goodsdata","画像データの取得");
        imageUrl = url;
//        ReceiveImageAsyncTask receiveImageAsyncTask = new ReceiveImageAsyncTask(url);
//        receiveImageAsyncTask.setImageReceive(new ImageReceive() {
//            @Override
//            public void receiveImage(Bitmap bm) {
//                picture = bm;
//                Log.d(getClass().getName(),"画像データの取得完了");
//                Log.d(getClass().getName(),picture.toString());
//            }
//        });
//        receiveImageAsyncTask.execute();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ranking_no);
        dest.writeString(goods_name);
        dest.writeInt(goods_id);
        dest.writeDouble(rate);
        dest.writeParcelable(picture, flags);
        dest.writeString(comment);
        dest.writeString(genre);
        dest.writeString(scene);
        dest.writeString(hobbies);
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }


//    public void paseData() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(goods_id);
//        sb.append(goods_name);
//        sb.append(valuation);
//        //sb.append(picture);
//        sb.append(comment);
//        sb.append(genre);
//        sb.append(scene);
//        sb.append(hobbies);
//    }
}

