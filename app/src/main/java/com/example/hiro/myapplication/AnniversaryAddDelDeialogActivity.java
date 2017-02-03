package com.example.hiro.myapplication;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.GregorianCalendar;

public class AnniversaryAddDelDeialogActivity extends AppCompatActivity{

    DatePicker mDatePicker = null;
    TextInputEditText mAnniversaryEditText = null;
    BootstrapButton mAddButton = null;
    BootstrapButton mCancelButton = null;

    //キーボードを操作するためのオブジェクト
    InputMethodManager inputMethodManager;

    //記念日の登録年月日
    GregorianCalendar date = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary_add_del_deialog);

        mDatePicker = (DatePicker)findViewById(R.id.datePicker);
        mAnniversaryEditText = (TextInputEditText)findViewById(R.id.AnniversaryEditText);
        mAddButton = (BootstrapButton)findViewById(R.id.AddButton);
        mCancelButton = (BootstrapButton)findViewById(R.id.CancelButton);

        //キーボード表示を制御するためのオブジェクト
        inputMethodManager =  (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);

        mAnniversaryEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //イベントを取得するタイミングには、ボタンが押されてなおかつエンターキーだったときを指定
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    //キーボードを閉じる
                    inputMethodManager.hideSoftInputFromWindow(mAnniversaryEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    return true;
                }
                return false;
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                Log.d("aaaaaaaaaaaaaaaaaaaaa",mDatePicker.toString());
                // keyword "RESULT" でデータの可算結果 value を返す
                intent.putExtra("Date",mDatePicker.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //Dialogを閉じる
            }
        });

    }

}
