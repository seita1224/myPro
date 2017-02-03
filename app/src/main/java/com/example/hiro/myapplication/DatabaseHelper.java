package com.example.hiro.myapplication;

/**
 * Created by ie4a on 2017/01/27.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context){
        super(context,"choome.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成。SQLの文法は通常のSQLiteと同様
        db.execSQL(
                "create table holiday_table ("
                        + "id integer primary key autoincrement,"
                        + "name text not null,"
                        + "date text not null,"
                        + "title text not null,"
                        + "rankinfo text not null);");

        //ユーザー情報テーブル（tokenとconnect)
        db.execSQL(
                "create table users_table ("
                        + "name text not null,"
                        + "sex text not null,"
                        + "age integer not null,"
                        + "hobby integer not null,"
                        + "connect integer not null,"
                        + "token text not null);");

        //keyテーブル(key)
        db.execSQL(
                "create table keys_table ("
                        + "key text not null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
