package com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by seita on 2016/12/02.
 */

public interface AsyncCallBack {
    void asyncCallBack(JSONObject jo);
    void asyncCallBack(JSONArray ja);
}
