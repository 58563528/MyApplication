package com.example.myinterview.myfragment.fragment.navigation;


import android.os.Bundle;

import com.example.myinterview.R;
import com.example.myinterview.myfragment.fragment.BaseFragment;


public class ToysFragment extends BaseFragment {

    public static ToysFragment newInstance(String title) {
        ToysFragment fragment = new ToysFragment();
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
    public void initView() {
    }

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_toy;
    }
}
