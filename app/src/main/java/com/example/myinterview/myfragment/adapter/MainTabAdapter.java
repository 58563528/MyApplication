package com.example.myinterview.myfragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mList;
    private String mTabTitle[] = new String[]{"科技", "游戏", "装备", "创业", "想法"};

    public MainTabAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }


}
