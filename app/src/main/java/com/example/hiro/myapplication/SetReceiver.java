package com.example.hiro.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by ie4a on 2017/01/26.
 */
public class SetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AlarmReceiver.class); // ReceivedActivityを呼び出すインテントを作成
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, i, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得


        calendar.setTimeZone(TimeZone.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 55);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE); // AlramManager取得

        int version = Build.VERSION.SDK_INT;
        if(version < 19){
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
        }else if(version >= 19){
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, sender);
        }
    }
}
