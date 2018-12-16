package com.example.myinterview.myfragment.fragment.tab;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.myinterview.R;
import com.example.myinterview.common.model.Photo;
import com.example.myinterview.myfragment.adapter.PhotoAdapter;
import com.example.myinterview.myfragment.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EquipmentFragment extends BaseFragment {

    private static final String TAG = "Ekko";

    private List<Photo> list = new ArrayList<>();
    private PhotoAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;

    public static EquipmentFragment newInstance(String title) {
        EquipmentFragment fragment = new EquipmentFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        list.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(photos.length);
            list.add(photos[index]);
        }
    }

    @Override
    public void refreshData() {
        Log.e(TAG, "EquipmentFragment - refreshData");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        //通知数据发生变化
                        adapter.notifyDataSetChanged();
                        //表示刷新事件结束，并隐藏刷新进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.science_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(fragmentActivity, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(list);
        recyclerView.setAdapter(adapter);

        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //应该为网络请求最新数据
                refreshData();
            }
        });
    }

    @Override
    public int setFragmentLayoutID() {
        return R.layout.fragment_equipment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setRefreshing(true);
    }
}
