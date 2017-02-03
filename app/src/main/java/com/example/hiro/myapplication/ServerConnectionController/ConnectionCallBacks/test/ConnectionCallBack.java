package com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.test;


import com.example.hiro.myapplication.ServerConnectionController.JsonParse.RankingJsonPase;

/**
 * Created by seita on 2016/12/02.
 */

public interface ConnectionCallBack {
    void receiveJson(RankingJsonPase rankingJsonPase);
}
