package com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main;

import com.example.hiro.myapplication.DBController.Goodsdata;

import java.util.ArrayList;

/**
 * Created by HIRO on 2017/01/23.
 */

interface SearchGoods {
    public void searchGoods(ArrayList<Goodsdata> goodsdatas, String connectionStatus);
}
