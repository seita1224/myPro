package com.example.hiro.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
//import android.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hiro.myapplication.DBController.Goodsdata;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionCallBacks.main.RankingReceive;
import com.example.hiro.myapplication.ServerConnectionController.ConnectionHelper;
import com.example.hiro.myapplication.maikeView.CardRecyclerView;

import java.util.ArrayList;

//AppCompatActivity
public class ConfirmationActivity extends Activity{
//    private SearchView searchView;
//    private String searchWord = "検索";
    String word;
    EditText edit;
    ListView lv;
    String members[];
    int count;
    ArrayAdapter<String> adapter;

    CardRecyclerView cardRecyclerView = null;
    LinearLayout layout = null;

    ConnectionHelper connectionHelper = null;

    ArrayList<Goodsdata> goodsList = null;

    String age,sex,scene,genre,hobbie,goodstype;

    Intent intent;

    //キーボード閉める用
    InputMethodManager inputMethodManager;
    RelativeLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //android:fitsSystemWindows="false

        //キーボードを閉じたいEditTextオブジェクト
        edit = (EditText) findViewById(R.id.search);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);


        edit.addTextChangedListener( new TextWatcher() {
            // 文字列sのなかで startの位置から開始されている字数countの文字が、
            // 字数lengthの古いテキストを置換するときに呼び出される。
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }

            // 文字列sのなかで startの位置から開始されている字数countの文字が、
            // 字数lengthの新しいテキストで置換されようとしているときに呼び出される。
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            // 文字列sのどこかで、テキストが変更されたときに呼び出される。
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(1<s.toString().length()){
                    Log.d("RankingResultActivity","Load RankingActivity");
                    //android側で入力された値を入れる
                    word = s.toString();
                    lv = (ListView)findViewById(R.id.searchlist);

//                    //ランキングアクティビティのレイアウトを保存
//                    layout = new LinearLayout(this);

                    //サーバからデータのダウンロードの準備
                    connectionHelper = new ConnectionHelper(getApplicationContext());


                    //ランキングデータ受信
                    connectionHelper.reciveGoodsSearch(word);

                    //データのダウンロード&ArrayListに格納&カードビューにセットの処理を
                    connectionHelper.setConnectionCallBack(new RankingReceive() {
                        @Override
                        public void rankReceive(ArrayList<Goodsdata> goodsdatas,String connectionStatus){
//                           Log.d("RankingResultActivity","Load CardView");
//                            cardRecyclerView.setRankingRecyclerAdapter(getApplicationContext(),goodsdatas);
//                            cardRecyclerView.setBackgroundResource(R.drawable.haikeiii);
//                            setContentView(cardRecyclerView);
//                            for(int i = 0; i < goodsdatas.size(); i++){
//                                count++;
//                          }
                            if(goodsdatas.size()==0){
                                //"閲覧履歴","あなたが投稿したレビュー"
                                String[] members = {"一致するプレゼントはありません。\nプレゼントを新規登録しますか?",};
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(),
                                        R.layout.custom_text_list_item3, members){
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

                                                Intent intent = new Intent(getApplication(),ProductregistrationActivity.class);
                                                intent.putExtra("DATA1",word);
                                                startActivity(intent);
                                                break;
                                        }
                                    }
                                });
                            }else {
                                adapter = new ArrayAdapter<String>(getApplication(), R.layout.custom_text_list_item2);
//                          members = new String[goodsdatas.size()];
                                for (int i = 0; i < goodsdatas.size(); i++) {
                                    adapter.add(goodsdatas.get(i).getGoods_name());
                                    Log.d("配列内", goodsdatas.get(i).getGoods_name());
                                }
                                lv = (ListView) findViewById(R.id.searchlist);
                                // アダプターを設定します
                                lv.setAdapter(adapter);
                                // リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        ListView listView = (ListView) parent;
                                        // クリックされたアイテムを取得します
                                        String item = (String) listView.getItemAtPosition(position);
                                        item = item.replaceAll(" ", "+");
                                        intent = new Intent(getApplication(),ReviewActivity.class);
                                        intent.putExtra("DATA1", item);
                                        startActivity(intent);
//                                    Toast.makeText(ListViewSampleActivity.this, item, Toast.LENGTH_LONG).show();
                                    }
                                });
// リストビューのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
                                lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        ListView listView = (ListView) parent;
                                        // 選択されたアイテムを取得します
                                        String item = (String) listView.getSelectedItem();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

//        //EditTextにリスナーをセット
//        edit.setOnKeyListener(new View.OnKeyListener() {
//            //コールバックとしてonKey()メソッドを定義
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                //イベントを取得するタイミングには、ボタンが押されてなおかつエンターキーだったときを指定
//                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
//                    //Enter時の処理
//
//                    inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//                    return true;
//                }
//                return false;
//            }
//        });

        edit.setOnKeyListener(new View.OnKeyListener() {
            //コールバックとしてonKey()メソッドを定義
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //イベントを取得するタイミングには、ボタンが押されてなおかつエンターキーだったときを指定
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    //Enter時の処理
                    String item = String.valueOf(((EditText)v).getText());
                    Intent intent = new Intent(getApplicationContext(),ConfirmationresultActivity.class);
                    intent.putExtra("goodsName",item);
                    Log.d("ConfirmationActivity","IntentStart");
                    startActivity(intent);

                    inputMethodManager.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                return false;
            }
        });


        //背景タップ時にキーボードを閉じる！！！！！
        ///////////////////
        //画面全体のレイアウト
        mainLayout = (RelativeLayout)findViewById(R.id.activity_confirmation);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();
        return false;
    }
}
