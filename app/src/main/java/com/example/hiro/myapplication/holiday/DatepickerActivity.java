package com.example.hiro.myapplication.holiday;

/*
ランキング選択
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.example.hiro.myapplication.AnniversaryAddDelDeialogActivity;
import com.example.hiro.myapplication.DatabaseHelper;
import com.example.hiro.myapplication.R;
import com.example.hiro.myapplication.RankingResultActivity;
import com.example.hiro.myapplication.ReviewActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class DatepickerActivity extends FragmentActivity{

    RadioGroup sexRadioGroup = null;

    Spinner ageSpinner = null;
    Spinner secenSpinner = null;
    Spinner genreSpinner = null;
    Spinner hobbiySpinner = null;

    TextView dateTextView;

    static String age = "0",sex = "0",scene = "0",genre = "0",hobbie = "0",goodstype = "1";

    //ランキング判別フラグ群
    boolean sexFlg,ageFrag,hobbiesFlg,secenFlg,genreFlg;
    static boolean selectFlg = false;

    BootstrapButton serchButton = null;
    BootstrapButton receivePrezentButton = null;
    BootstrapButton wantPrezentButton = null;

    Intent intent;
    TextView nengappi;
    EditText editText;
    DatePickerDialog.OnDateSetListener varDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker);

        editText = (EditText)findViewById(R.id.title);

        dateTextView = (TextView)findViewById(R.id.dateTextView);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dateDialog = new DatePickerDialog(
                        DatepickerActivity.this,
                        varDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dateDialog.show();
            }
        });
        varDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view , int year , int monthOfYear , int dayOfMonth){
                dateTextView.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }
        };

        Button button = (Button)findViewById(R.id.datepicker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dateDialog = new DatePickerDialog(
                        DatepickerActivity.this,
                        varDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dateDialog.show();

            }
        });

        //
        sexRadioGroup = (RadioGroup)findViewById(R.id.sex_radio_group);
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(R.id.raidiobutton_no  == checkedId){
                    sex = "0";
                    selectFlg = true;
                }else if(R.id.raidiobutton_man  == checkedId){
                    sex = "1";
                    selectFlg = true;
                }else{
                    sex = "2";
                    selectFlg = true;
                }
            }
        });
        sexRadioGroup.check(R.id.raidiobutton_no);

        ageSpinner = (Spinner)findViewById(R.id.Agespinner);
        secenSpinner = (Spinner)findViewById(R.id.Scenespinner);
        genreSpinner = (Spinner)findViewById(R.id.Genrespinner);
        hobbiySpinner = (Spinner)findViewById(R.id.Hobbiesspinner);

        ageSpinner.setFocusable(false);
        secenSpinner.setFocusable(false);
        genreSpinner.setFocusable(false);
        hobbiySpinner.setFocusable(false);



        //spinnerをでかくする
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(),R.layout.spinner,this.getResources().getStringArray(R.array.list_age));
        ageSpinner.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplication(),R.layout.spinner,this.getResources().getStringArray(R.array.list_scene));
        secenSpinner.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplication(),R.layout.spinner,this.getResources().getStringArray(R.array.list_genre));
        genreSpinner.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getApplication(),R.layout.spinner,this.getResources().getStringArray(R.array.list_hobbies));
        hobbiySpinner.setAdapter(adapter4);



        ageSpinner.setOnItemSelectedListener(selectedListener);
        secenSpinner.setOnItemSelectedListener(selectedListener);
        genreSpinner.setOnItemSelectedListener(selectedListener);
        hobbiySpinner.setOnItemSelectedListener(selectedListener);

        serchButton = (BootstrapButton)findViewById(R.id.Searchbutton);
        receivePrezentButton = (BootstrapButton)findViewById(R.id.button_receive_prezent);
        wantPrezentButton = (BootstrapButton)findViewById(R.id.button_want_prezent);


        serchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //データベースopen
                DatabaseHelper helper = new DatabaseHelper(getApplication());
                SQLiteDatabase mydb = helper.getWritableDatabase();

                //テストデータ
                ContentValues values = new ContentValues();
                values.put("name",editText.getText().toString());
                values.put("date",nengappi.getText().toString());
                values.put("title","テスト");
                values.put("rankinfo",sex + "," + age +  "," +scene +"," + genre + "," + hobbie + "," + goodstype);

                long ret;
                try {
                    ret = mydb.replace("holiday_table", null, values);
                } finally {

                }
                if (ret == -1) {
                    Toast.makeText(getApplication(), "失敗", Toast.LENGTH_SHORT).show();
                }

                mydb.close();
                intent = new Intent(getApplication(),HolidayActivity.class);
                startActivity(intent);
            }
        });

        receivePrezentButton.setOnClickListener(clickListener);
        wantPrezentButton.setOnClickListener(clickListener);
    }
    private static AdapterView.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner)parent;
            if(spinner.isFocusable() == false) {
                spinner.setFocusable(true);
            }else{
                Log.d("RankingActivity", String.valueOf(position));

                switch (spinner.getId()){
                    case R.id.Scenespinner:
                        scene = String.valueOf(position);
                        selectFlg = true;
                        break;
                    case R.id.Agespinner:
                        age = String.valueOf(position);
                        selectFlg = true;
                        break;
                    case R.id.Genrespinner:
                        genre = String.valueOf(position);
                        selectFlg = true;
                        break;
                    case R.id.Hobbiesspinner:
                        hobbie = String.valueOf(position);
                        selectFlg = true;
                        break;
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Log.d("RankingActivity", "Nothing");
        }
    };

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.button_receive_prezent) {
                goodstype = "1";
                selectFlg = true;
                receivePrezentButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                wantPrezentButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);

                secenSpinner.setVisibility(View.VISIBLE);
            } else {
                goodstype = "2";
                selectFlg = true;
                receivePrezentButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                wantPrezentButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);

                secenSpinner.setVisibility(View.INVISIBLE);
                secenSpinner.setSelection(0);
            }
        }
    };

//    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//            String message = intent.getStringExtra("Date");
//            nengappi.setText(message);
//    }
}
