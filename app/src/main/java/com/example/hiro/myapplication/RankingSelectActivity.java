package com.example.hiro.myapplication;


/*
このファイルは結合しないでください
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RankingSelectActivity extends AppCompatActivity {

    ListView rankingListView = null;
    boolean age,sex,scene,genre,hobbie;
    String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_select);

        rankingListView = (ListView)findViewById(R.id.rankingListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_list);

        String list[] = getResources().getStringArray(R.array.list_ranking);
        for(String str:list){
            adapter.add(str);
        }

        rankingListView.setAdapter(adapter);

        rankingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
