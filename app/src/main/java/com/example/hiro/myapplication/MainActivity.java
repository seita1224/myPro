package com.example.hiro.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activit);

        //----------------初期アラーム設定------------------
        Intent i = new Intent(getApplicationContext(), AlarmReceiver.class); // ReceivedActivityを呼び出すインテントを作成
        PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得


        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 24);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); // AlramManager取得

        int version = Build.VERSION.SDK_INT;
        if(version < 19){
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
        }else if(version >= 19){
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
        }

        //---------------------データベースopen---------------------

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase mydb = helper.getWritableDatabase();


        //------------------記念日・祝日格納(初期起動のようなシーンどこかで必ず行って下さい）-------------------------

        for (Calendar c : JapaneseHolidayUtils.getHolidaysOf(2017)) {
            String date = c.get(Calendar.YEAR) + "/" +
                    (c.get(Calendar.MONTH) + 1) + "/" +
                    c.get(Calendar.DAY_OF_MONTH);

            String name = JapaneseHolidayUtils.getHolidayName(c);

            ContentValues values = new ContentValues();
            values.put("name",name);
            values.put("date",date);
            values.put("title",name);
            values.put("subtitle",name);
            values.put("comment",name + "です！");

            long ret;
            try {
                //挿入の実行
                ret = mydb.replace("holiday_table", null, values);
                //データベースを閉じる
//                mydb.close();

            } finally {
                //データベースを閉じる
//                mydb.close();
            }
            if (ret == -1) {
                Toast.makeText(this, "追加失敗", Toast.LENGTH_SHORT).show();
            }
        }
        //テスト テストデータの挿入

        ContentValues values = new ContentValues();
        values.put("name","テスト");
        values.put("date","2017/2/11");
        values.put("title","テスト");
        values.put("subtitle","テスト");
        values.put("comment","テスト" + "です！");

        long ret;
        try {
            //挿入の実行
            ret = mydb.replace("holiday_table", null, values);
            //データベースを閉じる
//                mydb.close();

        } finally {
            //データベースを閉じる
//                mydb.close();
        }
        if (ret == -1) {
            Toast.makeText(this, "追加失敗", Toast.LENGTH_SHORT).show();
        }

        //データベースを閉じる
        mydb.close();

    }

}
