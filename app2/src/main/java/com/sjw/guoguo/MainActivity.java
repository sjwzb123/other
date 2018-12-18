package com.sjw.guoguo;

import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.BaseFragment;
import com.kuli.commlib.view.NoScrollViewPager;
import com.sjw.guoguo.Fragment.ActionFragment;
import com.sjw.guoguo.Fragment.CartFragment;
import com.sjw.guoguo.Fragment.GoodsFragment;
import com.sjw.guoguo.Fragment.InforFragment;
import com.sjw.guoguo.Fragment.MainFragment;
import com.sjw.guoguo.Fragment.MyFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2017/9/23.
 */

public class MainActivity extends BaseActivity {
    private MyAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    private TabLayout mTableLayout;
    private String[] titles = {"推荐", "购物车", "我的"};
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private RelativeLayout mRlTitle;
    private TextView mTvTitle;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initData();
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);
        mTableLayout = (TabLayout) findViewById(R.id.main_tab);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        mTableLayout.getTabAt(0).setIcon(R.drawable.tab_home_select);
//        mTableLayout.getTabAt(1).setIcon(R.drawable.ic_launcher);
//        mTableLayout.getTabAt(2).setIcon(R.mipmap.ic_launcher);
        mTableLayout.getTabAt(1).setIcon(R.drawable.tab_cart_select);
        mTableLayout.getTabAt(2).setIcon(R.drawable.tab_mine_select);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTvTitle.setText(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    mRlTitle.setVisibility(View.GONE);
                } else {
                    mRlTitle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {
        MainFragment mainFragment = new MainFragment();
        mFragmentList.add(mainFragment);
//        ActionFragment actionFragment = new ActionFragment();
//        mFragmentList.add(actionFragment);
//        InforFragment inforFragment = new InforFragment();
//        mFragmentList.add(inforFragment);
        CartFragment cartFragment = new CartFragment();
        mFragmentList.add(cartFragment);
        MyFragment myFragment = new MyFragment();
        mFragmentList.add(myFragment);

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
