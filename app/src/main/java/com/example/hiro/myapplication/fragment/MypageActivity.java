//出来てない


package com.example.hiro.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hiro.myapplication.HelpActivity;
import com.example.hiro.myapplication.holiday.HolidayActivity;
import com.example.hiro.myapplication.NoticeActivity;
import com.example.hiro.myapplication.R;
import com.example.hiro.myapplication.StatuschangeActivity;

/**
 * Created by HIRO on 2016/11/25.
 */

public class MypageActivity extends Fragment{
    ListView lv;
    Intent intent;
    Switch sw;
    TextView textView;

    public static MypageActivity newInstanse(){
        MypageActivity fragment = new MypageActivity();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_mypage,container,false);
        lv = (ListView)v.findViewById(R.id.mypage_list);
        textView = (TextView) v.findViewById(R.id.textView11);


        //文字を画面に合わせてでかくする！！
        setTextSizeByInch(R.id.activity_mypage2,v);

        //"閲覧履歴","あなたが投稿したレビュー"
        String[] members = {"プロフィール変更","スケジュール管理","通知設定","ヘルプ",};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.custom_text_list_item, members){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TextView view = (TextView)super.getView(position, convertView, parent);
                return view;
            }
        };
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long l) {

//                ListView listView =(ListView)parent;
//                String item=(String)lv.getItemAtPosition(position);

                switch (position){
                    case 0:
                        intent = new Intent(getContext(),StatuschangeActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(), HolidayActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(),NoticeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getContext(),HelpActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });

        return v;
    }
    private void setTextSizeByInch(int layoutid,View v) {
        // ディスプレイ情報を取得する
        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // ピクセル数（width, height）を取得する
        final int widthPx = metrics.widthPixels;
        final int heightPx = metrics.heightPixels;
        // dpi (xdpi, ydpi) を取得する
        final float xdpi = metrics.xdpi;
        final float ydpi = metrics.ydpi;
        // インチ（width, height) を計算する
        final float widthIn = widthPx / xdpi;
        final float heightIn = heightPx / ydpi;
        // 画面サイズ（インチ）を計算する
        final double in = Math.sqrt(widthIn * widthIn + heightIn * heightIn);
        // 4インチ以上は比率に応じて文字を拡大
        if (in > 4) {
            // 親のレイアウトを取得
            ViewGroup parent = (ViewGroup)v.findViewById(layoutid);
            setTextSizes(parent, in / 4);
        }
    }

    /**
     * 親のレイアウトに設定されている子Viewの文字を画面サイズに応じて倍加します。
     */
    private void setTextSizes(ViewGroup parent, double multiple) {
        for(int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof ViewGroup) {
                setTextSizes((ViewGroup)view, multiple);
            } else if (view instanceof TextView) {
                TextView targetView = (TextView) view;
                targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(targetView.getTextSize() * multiple));
            }
        }
    }
}