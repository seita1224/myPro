package com.example.hiro.myapplication;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ie4a on 2017/01/26.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //----------------connect取得の非同期を実装して下さい---------------------

        context.startService(new Intent(context, StatusService.class));
    }

    public static class StatusService extends IntentService {

        public StatusService(){
            super("StatusService");
        }

        //日付差分を求めるメソッド
        public static int getDiffDays(Date fromDate, Date toDate){
            int  diffDays = 0;

            if(fromDate != null && toDate != null) {
                // // getTimeメソッドで経過ミリ秒を取得し、２つの日付の差を求める
                long fromDateTime = fromDate.getTime();
                long toDateTime = toDate.getTime();

                // 経過ミリ秒÷(1000ミリ秒×60秒×60分×24時間)。端数切り捨て。
                diffDays = (int)(( toDateTime - fromDateTime  ) / (1000 * 60 * 60 * 24 ));
            }

            return diffDays;
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            //データベースOpen
            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase mydb = helper.getWritableDatabase();

            //現在の日付を取得
            Calendar now = Calendar.getInstance();

            //----------------追加 1月1日判定-----------------------
            int day = now.get(Calendar.DATE);
            int month = now.get(Calendar.MONTH);

            if(month == 0 & day == 1){

                int fyear = now.get(Calendar.YEAR);
                fyear += 1;

                for (Calendar c : JapaneseHolidayUtils.getHolidaysOf(fyear)) {
                    String date = c.get(Calendar.YEAR) + "/" +
                            (c.get(Calendar.MONTH) + 1) + "/" +
                            c.get(Calendar.DAY_OF_MONTH);

                    String name = JapaneseHolidayUtils.getHolidayName(c);

                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("date", date);
                    values.put("title", name);
                    values.put("rankid","1");

                    Log.d("test",name + " " + date);

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

            }

            Date nowdate = now.getTime();

            String sql =
                    "SELECT date,name,id,rankinfo " +
                            "FROM holiday_table;";
            ArrayList<String> a = new ArrayList<String>();

            //SQLの実行
            Cursor cursor = mydb.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                do{
                    //DBに登録されているholidayの日付を順次格納する
                    String holiday = cursor.getString(0);
                    String title = cursor.getString(1);
                    int id = cursor.getInt(2);
                    String rankinfo = cursor.getString(3);

                    Log.d("test",rankinfo);

                    //holidayはString型なのでDate型に変換する
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    int diff = 0;

                    try {
                        Date holidate = format.parse(holiday);
                        diff = getDiffDays(nowdate,holidate) + 1;
                        Log.d("test",title + "：でぃふ" + Integer.toString(diff));

                        //日付差が１０日なら
                        if(diff == 10){
                            //ステータスバークリック時に開くページを指定する
                            Intent i = new Intent(getApplicationContext(),RankingResultActivity.class);
                            //intent.setClassName(getApplicationContext(),RankingResultActivity.class.getName());

                            //rankinfo 分解
                            String[] rankarray = rankinfo.split(",",0);
                            String sex = rankarray[0];
                            String age = rankarray[1];
                            String scene = rankarray[2];
                            String genre = rankarray[3];
                            String hobbie = rankarray[4];
                            String goodstype = rankarray[5];

                            Log.d("test",sex + age + scene);

                            i.putExtra("sex",sex);
                            i.putExtra("age",age);
                            i.putExtra("scene",scene);
                            i.putExtra("genre",genre);
                            i.putExtra("hobbie",hobbie);
                            i.putExtra("goodstype",goodstype);

                            int random_id = (int)(Math.random()*10000);


                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), random_id, i, PendingIntent.FLAG_UPDATE_CURRENT);

                            //アイコン設定
                            Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.choomeicon);


                            NotificationCompat.Builder builder =  new NotificationCompat.Builder(getApplicationContext());
                            builder.setContentIntent(pi);
                            //タイトル
                            builder.setTicker("10日後は" + title + "です！");
                            //大きいアイコン
                            builder.setLargeIcon(largeIcon);
                            builder.setSmallIcon(R.drawable.choomeicon);
                            //サブタイトル
                            builder.setContentTitle("10日後は" + title + "です！");
                            //コメント
                            builder.setContentText(title + "ランキングを見る");
                            builder.setAutoCancel(true);

                            //ステータスバー作成
                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(0, builder.build());

                        //日付が明日なら
                        }else if(diff == 1){
                            //ステータスバークリック時に開くページを指定する
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);

                            //rankinfo 分解
                            String[] rankarray = rankinfo.split(",",0);
                            String sex = rankarray[0];
                            String age = rankarray[1];
                            String scene = rankarray[2];
                            String genre = rankarray[3];
                            String hobbie = rankarray[4];
                            String goodstype = rankarray[5];

                            i.putExtra("sex",sex);
                            i.putExtra("age",age);
                            i.putExtra("scene",scene);
                            i.putExtra("genre",genre);
                            i.putExtra("hobbie",hobbie);
                            i.putExtra("goodstype",goodstype);

                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                            //アイコン設定
                            Bitmap largeIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.choomeicon);


                            NotificationCompat.Builder builder =  new NotificationCompat.Builder(getApplicationContext());
                            builder.setContentIntent(pi);
                            //タイトル
                            builder.setTicker("明日は" + title + "です！");
                            //大きいアイコン
                            builder.setLargeIcon(largeIcon);
                            builder.setSmallIcon(R.drawable.choomeicon);
                            //サブタイトル
                            builder.setContentTitle("明日は" + title + "です！");
                            //コメント
                            builder.setContentText("プレゼントを贈りましょう！");
                            builder.setAutoCancel(true);

                            //ステータスバー作成
                            NotificationManager manager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            manager.notify(0, builder.build());
                        }else if(diff < 0){
                            mydb.delete("holiday_table","id = "+Integer.toString(id),null);
                        }

                    } catch ( ParseException e ) {

                        Log.d("error","エラー");
                    }

                }while(cursor.moveToNext());
            }

            mydb.close();

        }
    }


}
