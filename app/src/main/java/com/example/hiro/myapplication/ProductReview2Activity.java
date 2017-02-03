package com.example.hiro.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProductReview2Activity extends AppCompatActivity {

    TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_review2);

        mText = (TextView)findViewById(R.id.infomationTextView);
        Intent intent = getIntent();
        String str = intent.getStringExtra("goodsName");
        mText.setText(str);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
