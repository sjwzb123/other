package com.sjw.guoguo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuli.commlib.BaseFragment;
import com.sjw.guoguo.R;

/**
 * Created by sjw on 2018/2/9.
 */

public class MainFragment extends BaseFragment {
    private ViewPager mVp;
    private TabLayout mTabLayout;
    private String[] TITLS = { "水果", "蔬菜", "干果", "整件" };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mVp = (ViewPager)view.findViewById(R.id.vp);
        mVp.setOffscreenPageLimit(3);
        mVp.setAdapter(new MyAdapter(getActivity().getSupportFragmentManager()));
        mTabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mVp);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    class MyAdapter extends FragmentPagerAdapter {
        private int mCount = TITLS.length;
        private int[] mColors = new int[] { android.R.color.holo_orange_dark, android.R.color.holo_green_dark, android.R.color.holo_blue_dark };

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new GoodsFragment();
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLS[position];
        }
    }
}
