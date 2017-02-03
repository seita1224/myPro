package com.example.hiro.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hiro.myapplication.fragment.FavoritesFragment;
import com.example.hiro.myapplication.fragment.HomeFragment;
import com.example.hiro.myapplication.fragment.MypageActivity;
import com.example.hiro.myapplication.fragment.RankingActivity;

import static java.security.AccessController.getContext;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //ViewPager viewPager;

    TabLayout mTabLayout;
    TabLayout.Tab mHomeTab;
    TabLayout.Tab mRankingTab;
    TabLayout.Tab mFavoritesTab;
    TabLayout.Tab mMaypageTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        setTextSizeByInch(R.id.tabs);


        //FloatingActionButtonのイベント
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ConfirmationActivity.class);
                startActivity(intent);
            }
        });

        //viewPager = (ViewPager) findViewById(R.id.pager);


        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setOnTabSelectedListener(this);

//        mHomeTab = mTabLayout.newTab().setText("ホーム");
        mRankingTab = mTabLayout.newTab().setText("ランキング");
//        mFavoritesTab = mTabLayout.newTab().setText("商品一覧");
        mMaypageTab = mTabLayout.newTab().setText("マイページ");

//        mTabLayout.addTab(mHomeTab);
        mTabLayout.addTab(mRankingTab);
//        mTabLayout.addTab(mFavoritesTab);
        mTabLayout.addTab(mMaypageTab);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mTabLayout.getTabAt(0).select();
//        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            //ページの呼び出し？
//            @Override
//            public Fragment getItem(int position) {
//                return TestFragment.newInstance(position + 1);
//            }
//
//            //タブ名
//            @Override
//            public CharSequence getPageTitle(int position) {
//
//
//
//                String title = "";
//                switch (position){
//                    case 0:
//                        title = "ホーム";
//                        break;
//                    case 1:
//                        title = "ランキング";
//                        break;
//                    case 2:
//                        title = "商品一覧";
//                        break;
//                    case 3:
//                        title = "マイページ";
//                        break;
//
//                }
//
//
//
//                return title;
//            }
//
//            //タブ数
//            @Override
//            public int getCount() {
//                return 4;
//            }
//        };
//
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(this);
//
//        //オートマチック方式: これだけで両方syncする
//        tabLayout.setupWithViewPager(viewPager);

        //マニュアル方式: これでViewPagerのPositionとTabのPositionをsyncさせるらしい
        //tabLayout.setTabsFromPagerAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }


    private void setTextSizeByInch(int layoutid) {
        // ディスプレイ情報を取得する
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
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
            ViewGroup parent = (ViewGroup)findViewById(layoutid);
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
                targetView.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)(targetView.getTextSize() * multiple));
            }
        }
    }




    @Override
    public void onTabSelected(TabLayout.Tab tab) {
//        if(tab == mHomeTab) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.container, HomeFragment.newInstance(5), HomeFragment.class.getSimpleName())
//                    .commit();
//        }else
        if(tab == mRankingTab) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, RankingActivity.newInstanse(), RankingActivity.class.getSimpleName())
                    .commit();
        }
//        else if(tab == mFavoritesTab) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.container, FavoritesFragment.newInstance(), FavoritesFragment.class.getSimpleName())
//                    .commit();
//        }
        else if(tab == mMaypageTab) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, MypageActivity.newInstanse(), MypageActivity.class.getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //ページの中身？？
//    public static class TestFragment extends Fragment{
//
//        FragmentManager fragmentManager = null;
//        FragmentTransaction fragmentTransaction = null;
//        MypageActivity fragment = MypageActivity.newInstanse();
//
//        public TestFragment() {
//        }
//
//        public static TestFragment newInstance(int page) {
//            Bundle args = new Bundle();
//            args.putInt("page", page);
//            TestFragment fragment = new TestFragment();
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Nullable
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            int page = getArguments().getInt("page", 0);
//            View view;
//            view= inflater.inflate(R.layout.fragment_test, container, false);
//
//            fragmentManager = getFragmentManager();
//            fragment = MypageActivity.newInstanse();
//
//            //レイアウト読み込み
//            switch (page -1){
//                case 0:
//                    view= inflater.inflate(R.layout.activity_confirmation, container, false);
//
//
//                    break;
//                case 1:
//                    view= inflater.inflate(R.layout.fragment_test2, container, false);
//                    break;
//                case 2:
//                    view= inflater.inflate(R.layout.fragment_test3, container, false);
//                    break;
//                case 3:
//                    break;
//            }
//            //((TextView) view.findViewById(R.id.page_text)).setText(" " + page);
//            return view;
//        }
//    }
}