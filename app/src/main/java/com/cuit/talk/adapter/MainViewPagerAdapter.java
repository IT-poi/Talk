package com.cuit.talk.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by inori on 16/10/8.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mViewList;

    private List<String> titleList;

    public MainViewPagerAdapter(FragmentManager fm, List<Fragment> mViewList, List<String> titleList) {
        super(fm);
        this.mViewList = mViewList;
        this.titleList = titleList;
    }


    @Override
    public int getCount() {
        return titleList.size();//页卡数
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//页卡标题
    }
}
