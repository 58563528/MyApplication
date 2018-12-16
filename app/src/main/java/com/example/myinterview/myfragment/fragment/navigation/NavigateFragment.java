package com.example.myinterview.myfragment.fragment.navigation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.myinterview.R;
import com.example.myinterview.myfragment.adapter.MainTabAdapter;
import com.example.myinterview.myfragment.fragment.BaseFragment;
import com.example.myinterview.myfragment.fragment.tab.BusinessFragment;
import com.example.myinterview.myfragment.fragment.tab.EquipmentFragment;
import com.example.myinterview.myfragment.fragment.tab.GameFragment;
import com.example.myinterview.myfragment.fragment.tab.ScienceFragment;
import com.example.myinterview.myfragment.fragment.tab.ThinkingFragment;

import java.util.ArrayList;
import java.util.List;


public class NavigateFragment extends BaseFragment {

    private static final String TAG = "Ekko";
    public TabLayout tabLayout;
    public ViewPager viewPager;
    private List<Fragment> fragmentList;

    public static NavigateFragment newInstance(String title) {
        NavigateFragment fragment = new NavigateFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initData() {

    }

    @Override
    public void refreshData() {

    }

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_navigate;
    }

    @Override
    public void initView() {
        fragmentList = new ArrayList<>();

        //在OnCreateView之后，绑定控件
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        ScienceFragment science = ScienceFragment.newInstance("Science");
        GameFragment game = GameFragment.newInstance("Game");
        EquipmentFragment equipment = EquipmentFragment.newInstance("Equipment");
        BusinessFragment business = BusinessFragment.newInstance("Business");
        ThinkingFragment thinking = ThinkingFragment.newInstance("Thinking");

        fragmentList.add(science);
        fragmentList.add(game);
        fragmentList.add(equipment);
        fragmentList.add(business);
        fragmentList.add(thinking);

        MainTabAdapter adapter = new MainTabAdapter(fragmentActivity.getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
    }

}
