package com.example.hiro.myapplication;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        //起動画面:ロゴのアニメーション設定
        ImageView img = (ImageView)findViewById(R.id.robot);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.alpha);
        set.setTarget(img);
        set.start();

        Handler handler = new Handler();
        // 2000ms遅延させてsplashHandlerを実行。
        handler.postDelayed(new splashHandler(), 1500);

        boolean check = AppLaunchChecker.hasStartedFromLauncher(getApplicationContext());
        if(!check){
            AppLaunchChecker.onActivityCreate(this);


            //----------------初期アラーム設定------------------
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class); // ReceivedActivityを呼び出すインテントを作成
            i
            PendingIntent sender = PendingIntent.getBroadcast(StartActivity.this, 0, i, 0); // ブロードキャストを投げるPendingIntentの作成

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

            Calendar yearcal = Calendar.getInstance();

            int year = yearcal.get(Calendar.YEAR);

            for(int ci=0; ci<=1;ci++) {
                year += ci;

                for (Calendar c : JapaneseHolidayUtils.getHolidaysOf(year)) {
                    String date = c.get(Calendar.YEAR) + "/" +
                            (c.get(Calendar.MONTH) + 1) + "/" +
                            c.get(Calendar.DAY_OF_MONTH);

                    String name = JapaneseHolidayUtils.getHolidayName(c);

                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("date", date);
                    values.put("title", name);
                    values.put("rankinfo","1");

                    long ret;
                    try {
                        //挿入の実行
                        ret = mydb.replace("holiday_table",null, values);
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
            mydb.close();
        }
    }

    class splashHandler implements Runnable {
        public void run() {
//            //インテントを生成し、遷移先のアクティビティクラスを指定する。
//            Intent intent = new Intent( getApplication(),LoginActivity.class);
            Intent intent;

            SharedPreferences tokenShardPreferences = getSharedPreferences("token",Context.MODE_PRIVATE);
            String token = tokenShardPreferences.getString("token",null);


            if(token == null){
                intent = new Intent(getApplication(),LoginActivity.class);
            }else{
                intent = new Intent(getApplication(),TabActivity.class);
            }

            //次のアクティビティの起動
            startActivity(intent);
            //スプラッシュの終了。
            finish();
        }
    }
}