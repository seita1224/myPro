package com.example.hiro.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by 2130085 on 2016/11/24.
 */

public class ProductregistrationActivity extends Activity{
    private static  final  int RESULT_PICK_IMAGEFILE = 1001;
    private ImageView imageView;
    private Button button;

    String data1;


    //深見大樹
    Spinner secenSpinner = null;
    BootstrapButton receivePrezentButton = null;
    BootstrapButton wantPrezentButton = null;
    TextView gazousentaku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productregistration);

        Intent intent = getIntent();
        data1 = intent.getStringExtra("DATA1");
        EditText syname = (EditText)findViewById(R.id.syname);
        syname.setText(data1);



        //画像選択＆その他
        //深見大樹
        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        gazousentaku = (TextView)findViewById(R.id.gazousentaku);

        //ratingBar
        RatingBar bar = (RatingBar) findViewById(R.id.scoreBar);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                String str = String.valueOf(ratingBar.getRating());
            }
        });
        //

        //spinner設定
        final Spinner spinner = (Spinner) findViewById(R.id.genreSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner1 = (Spinner) adapterView;
                String spi = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final Spinner spinner1 = (Spinner)findViewById(R.id.sceneS) ;
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner2 = (Spinner) adapterView;
                String spi2 = spinner1.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner,this.getResources().getStringArray(R.array.list_genre));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner,this.getResources().getStringArray(R.array.list_scene));

        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter2);
//        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
//        //

        //登録ボタン
        Button pregiButton = (Button)findViewById(R.id.pregiButton);
        pregiButton.setOnClickListener(new View.OnClickListener() {

            //
            @Override
            public void onClick(View view) {
                EditText syname = (EditText)findViewById(R.id.syname);
                EditText reviewText = (EditText)findViewById(R.id.reviewText);



                //Intent intent = new Intent(ProductregistrationActivity.this,ConfirmationresultActivity.class);
                //startActivity(intent);
            }
        });


        //画像参照ボタン
        Button RefeButton = (Button) findViewById(R.id.RefeButton);
        RefeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("iamge/*");
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);

                gazousentaku.setVisibility(View.INVISIBLE);
            }
        });


        //深見大樹
        secenSpinner = (Spinner)findViewById(R.id.sceneS);
        receivePrezentButton = (BootstrapButton)findViewById(R.id.button_receive_prezent);
        wantPrezentButton = (BootstrapButton)findViewById(R.id.button_want_prezent);
        receivePrezentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goodstype = "1";
                //selectFlg = true;
                receivePrezentButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);
                wantPrezentButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);

                secenSpinner.setVisibility(View.VISIBLE);
            }
        });
        wantPrezentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goodstype = "2";
                //selectFlg = true;
                receivePrezentButton.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                wantPrezentButton.setBootstrapBrand(DefaultBootstrapBrand.WARNING);

                secenSpinner.setVisibility(View.INVISIBLE);
                secenSpinner.setSelection(0);
            }
        });
    }

    private String getGalleryPath(){
        return Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/";}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("", "Uri:" + uri.toString());

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }



}

