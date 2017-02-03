package com.example.hiro.myapplication.DBController;

import android.app.Application;

/**
 * Created by seita_v on 2016/10/21.
 */

//ユーザー情報を扱うクラス
public class Userdata extends Application{
    //フィールド
    private String e_mail;//メールアドレス
    private String password;
    private String name;//名前
    private String sex;//性別
    private int hobbies;//趣味
    private int id;//ユーザーID
    private int Age;//年齢
    private String token;//トークン情報

    //コンストラクタ
    public Userdata() {}

    public void UserdataReset(){
        e_mail = "";
        password = "";
        name = "";
        sex = "";
        hobbies = 0;
        id = 0;
        Age = 0;
        token = "";
    }


    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //e-Mail
    public String getEmail() {
        return e_mail;
    }
    public void setEmail(String email) {
        this.e_mail = email;
    }

    //name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //age
    public int getAge() {
        return Age;
    }
    public void setAge(int age) {
        Age = age;
    }

    //sex
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    //hobbies
    public int getHobbies() {
        return hobbies;
    }
    public void setHobby(int hobbies) {
        this.hobbies = hobbies;
    }

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public String getPassword() {return password;}
    public void setPassword(String password) {
        this.password = password;
    }


//    public void paseData() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(id);
//        sb.append(e_mail);
//        sb.append(name);
//        sb.append(sex);
//        sb.append(hobbies);
//        sb.append(Age);
//    }
}