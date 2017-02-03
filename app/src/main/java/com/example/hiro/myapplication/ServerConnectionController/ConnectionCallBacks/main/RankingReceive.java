package com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main;


import com.example.hiro.myapplication.DBController.Goodsdata;

import java.util.ArrayList;

/**
 * Created by seita on 2016/12/05.
 */

public interface RankingReceive {
    public void rankReceive(ArrayList<Goodsdata> goodsdatas, String connectionStatus);
}
