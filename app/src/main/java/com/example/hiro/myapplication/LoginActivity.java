package com.example.hiro.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiro.myapplication.DBController.Userdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.AsyncCallBack;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.UserSend;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;
import com.example.hiro.myapplication.ServerConnectionController.JsonParse.RankingJsonPase;
import com.example.hiro.myapplication.ServerConnectionController.ReceiveJsonAsyncTask;

import org.json.JSONObject;

/**
 * Created by 2130085 on 2016/10/28.
 */

public class LoginActivity extends Activity{
    Intent intent;

    TextView loginErrorTextView;
    boolean authenticationFlg;

    EditText passEditText,emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginErrorTextView = (TextView)findViewById(R.id.loginErrorTextView);

        TextView link = (TextView) findViewById(R.id.textView4);
        Button btn = (Button) findViewById(R.id.button);
        passEditText = (EditText) findViewById(R.id.editText);
        emailEditText = (EditText) findViewById(R.id.editText2);

        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        Button btn2 = (Button) findViewById(R.id.button2);


        btn.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                intent = new Intent( getApplication(),TabActivity.class);

                Userdata userdata = new Userdata();

                userdata.setEmail(emailEditText.getText().toString());
                userdata.setPassword(passEditText.getText().toString());

                ConnectionHelper connectionHelper = new ConnectionHelper(getApplicationContext());
                connectionHelper.setConnectionCallBack(new UserSend() {
                    @Override
                    public void responseUserMessage(String message) {
                        if(message.equals("メールアドレスがありません")){
                            authenticationFlg = false;
                        }else if(message.equals("パスワードがありません")){
                            authenticationFlg = false;
                        }
                        if(authenticationFlg == false){
                            return;
                        }
                        Log.d(getClass().getName(),message);
                        SharedPreferences token = getSharedPreferences("token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = token.edit();
                        editor.putString("token",message);
                        editor.apply();
                    }
                });

                connectionHelper.sendLoginUser(userdata);
                if (authenticationFlg == false){
                    loginErrorTextView.setText("メールアドレスまたはパスワードが違います");
                    return;
                }
                //次のアクティビティの起動
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            // このメソッドがクリック毎に呼び出される
            public void onClick(View v) {
                intent = new Intent(getApplication(),MailActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login(){

    }
/**
        link.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,"".class);
                startActivity(intent);
            }
        });

        link2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this,"".class);
                startActivity(intent);
            }
        });

        bth.setOnClickListener(new View.OnClickListener() {
        @Override
            public  void onClick(View v){
               Intent intent = new Intent(LoginActivity.this,"".class);
                startActivity(intent);
            }
        });
*/


}
