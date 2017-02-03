//出来てる！！！！！！！！

package com.example.hiro.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class ConfigurationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,getResources().getStringArray(R.array.agelist));

        //ポイント②「android.R.layout.simple_spinner_dropdown_item」ではなく、自作のレイアウト「spinner_dropdown_item.xml」を指定する
        //これによりスピナーを選択した際のドロップダウンリストのtextSize等を個別に設定出来るようになる。
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.age);
        spinner.setAdapter(adapter);

        // スピナーのアイテムが選択された時の動作を設定
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //スピナー内のアイテムが選択された場合の処理をここに記載
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //スピナーでは呼ばれない模様。ただし消せないので「おまじない」として残す。
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,getResources().getStringArray(R.array.hobbylist));

        //ポイント②「android.R.layout.simple_spinner_dropdown_item」ではなく、自作のレイアウト「spinner_dropdown_item.xml」を指定する
        //これによりスピナーを選択した際のドロップダウンリストのtextSize等を個別に設定出来るようになる。
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        Spinner spinner2 = (Spinner) findViewById(R.id.hobby);
        spinner2.setAdapter(adapter2);

        // スピナーのアイテムが選択された時の動作を設定
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //スピナー内のアイテムが選択された場合の処理をここに記載
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                //スピナーでは呼ばれない模様。ただし消せないので「おまじない」として残す。
            }
        });
    }
}