package com.example.hiro.myapplication.holiday;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.hiro.myapplication.AddActivity;
import com.example.hiro.myapplication.AnniversaryAddDelDeialogActivity;
import com.example.hiro.myapplication.ConfirmationActivity;
import com.example.hiro.myapplication.DatabaseHelper;
import com.example.hiro.myapplication.LoginActivity;
import com.example.hiro.myapplication.R;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HolidayActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    ConnectionHelper connectionHelper = null;
    int tappedPosition = 0;
    ArrayList<HashMap<String, String>> list_data;
    SimpleAdapter simp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);

        lv = (ListView) findViewById(R.id.listview);


        //FloatingActionButtonのイベント
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DatepickerActivity.class);
                startActivity(intent);
            }
        });

        //データベースopen
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase mydb = helper.getWritableDatabase();
//
//        //テストデータ
//        ContentValues values = new ContentValues();
//        values.put("name", "テスト");
//        values.put("date", "2017/2/17");
//        values.put("title", "テスト");
//        values.put("rankinfo","tetetet");

//        long ret;
//        try {
//            ret = mydb.replace("holiday_table", null, values);
//        } finally {
//
//        }
//        if (ret == -1) {
//            Toast.makeText(this, "失敗", Toast.LENGTH_SHORT).show();
//        }

        String sql = "SELECT date,name,rankinfo " + "FROM holiday_table;";

        list_data = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashTmp = new HashMap<String, String>();

        Cursor cursor = mydb.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do{

                hashTmp.put("main", cursor.getString(1));
                hashTmp.put("sub", cursor.getString(0));
                list_data.add(new HashMap<String, String>(hashTmp));

            }while (cursor.moveToNext());
        }


        simp = new SimpleAdapter(getApplicationContext(),list_data, R.layout.list_item,
                new String[]{"main", "sub"}, new int[]{R.id.holidayname, R.id.holidaydate});


        lv.setAdapter(simp);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String string = ""+i;
                Log.d("aaaaaaaaaaaa",string);
                tappedPosition = i;


                registerForContextMenu(lv);
                return false;
            }
        });


        mydb.close();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "削除");
        menu.add(0, 10, 1, "キャンセル");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0 :
                Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
                int position = tappedPosition;

                // それぞれの要素を削除
                String name = list_data.get(position).get("main");
                String date = list_data.get(position).get("sub");

                DatabaseHelper helper = new DatabaseHelper(this);
                SQLiteDatabase mydb = helper.getWritableDatabase();

                mydb.delete("holiday_table","name = " + "\'" + name + "\' " + "and date = \'" + date + "\'" ,null);

                mydb.close();

                list_data.remove(position);

                // ListView の更新
                simp.notifyDataSetChanged();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getApplication(),AnniversaryAddDelDeialogActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
