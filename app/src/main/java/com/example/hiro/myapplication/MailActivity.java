package com.example.hiro.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hiro.myapplication.DBController.Userdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.UserReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.UserSend;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;

import java.util.regex.Pattern;

/**
 * Created by 2130085 on 2016/11/21.
 */

public class MailActivity extends Activity{

    Spinner hobbiySpinner;

    EditText mail,pass,passReview,username,ageText;
    String mail_s,pass_s,passReview_s,username_s;
    TextView errorTextView;

    private String email_pattern = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";

    Button mailRegiButton;

    boolean inputFlg = true;

    String age = "0",sex = "0",scene = "0",genre = "0",hobbie = "0",goodstype = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);

        errorTextView = (TextView)findViewById(R.id.errorTextView);

        mail = (EditText) findViewById(R.id.mailText);
        pass = (EditText) findViewById(R.id.passText);
        passReview = (EditText) findViewById(R.id.passReviewText);
        username = (EditText) findViewById(R.id.username);
        ageText = (EditText)findViewById(R.id.ageText);

        RadioGroup sexRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(R.id.man  == checkedId){
                    sex = "男";
                }else if(R.id.woman  == checkedId){
                    sex = "女";
                }
            }
        });
        sexRadioGroup.check(R.id.man);

        hobbiySpinner = (Spinner)findViewById(R.id.hobby);

        hobbiySpinner.setFocusable(false);


        hobbiySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                hobbie = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mailRegiButton = (Button) findViewById(R.id.mailRegiButton);
        mailRegiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(getClass().getName(),"test");

                Userdata userdata = new Userdata();
                ConnectionHelper connectionHelper = new ConnectionHelper(getApplicationContext());

                connectionHelper.setConnectionCallBack(new UserSend() {
                    @Override
                    public void responseUserMessage(String message) {
                        Log.d(getClass().getName(),message);

                        SharedPreferences token = getSharedPreferences("token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = token.edit();
                        editor.putString("token",message);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(),TabActivity.class);
                        startActivity(intent);
                    }
                });

                mail_s =  mail.getText().toString();
                pass_s = pass.getText().toString();
                passReview_s = passReview.getText().toString();
                username_s = username.getText().toString();
                age = ageText.getText().toString();

                inputFlg = true;

                String errorMessage = "";

                if(mail_s.matches(email_pattern)){
                    errorMessage = "正しいメールアドレスを入力してください";
                    Log.e(getClass().getName(),errorMessage);
                    inputFlg = false;
                }

                try {
                    Integer.parseInt(age);
                }catch (NumberFormatException e){
                    errorTextView.setText("年齢には数字を入力してください");
                    return;
                }

                if(inputFlg == true){
                    if (mail_s.equals("")){
                        errorMessage = "メールアドレスを入力してください";
                        inputFlg = false;
                    } else if (pass_s.equals("")){
                        errorMessage = "パスワードを入力してください";
                        inputFlg = false;
                    } else if (username_s.equals("")){
                        errorMessage = "ユーザーネームを入力してください";
                        inputFlg = false;
                    } else if (age.equals("")){
                        errorMessage = "年齢を入力してください";
                        inputFlg = false;
                    } else if (sex.equals("")){
                        errorMessage = "性別を入力してください";
                        inputFlg = false;
                    } else if (hobbie.equals("")){
                        errorMessage = "趣味を入力してください";
                        inputFlg = false;
                    }
                }

                if(inputFlg == false){
                    Log.e(getClass().getName(),errorMessage);
                    errorTextView.setText(errorMessage);
                    return;
                }
                Log.e(getClass().getName(),errorMessage);

                userdata.setAge(Integer.parseInt(age));
                userdata.setEmail(mail_s);
                userdata.setPassword(pass_s);
                userdata.setHobby(Integer.parseInt(hobbie));
                userdata.setName(username_s);
                userdata.setSex(sex);

                connectionHelper.sendRegistrationUser(userdata);
            }
        });
    }

}
