package com.example.myinterview.myfragment.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinterview.R;
import com.example.myinterview.common.model.Photo;


public abstract class BaseFragment extends Fragment {

    public Photo[] photos = {
            new Photo("Kobe", R.mipmap.nba1),
            new Photo("Miami", R.mipmap.nba2),
            new Photo("Griffin", R.mipmap.nba3),
            new Photo("Kobe", R.mipmap.nba4),
            new Photo("Kobe", R.mipmap.nba5),
            new Photo("Jordan", R.mipmap.nba6),
            new Photo("James", R.mipmap.nba7),
            new Photo("Kobe", R.mipmap.nba8),
            new Photo("Jordan", R.mipmap.nba9),
            new Photo("Kobe", R.mipmap.nba10),
            new Photo("Irving", R.mipmap.nba11),
            new Photo("Kobe", R.mipmap.nba12),
            new Photo("Kobe", R.mipmap.nba13),
            new Photo("Kobe", R.mipmap.nba14),
            new Photo("1996", R.mipmap.nba15),
            new Photo("Kobe", R.mipmap.nba16),
            new Photo("Kobe", R.mipmap.nba17)};

    private final String TAG = "Ekko - " + this.getClass().getSimpleName();
    private final String TAG1 = "Ekko - " + this.getClass().getSimpleName();

    public boolean isViewCreated = false;
    private boolean isFirst = true;

    public FragmentActivity fragmentActivity;
    //public Activity activity;

    public View rootView;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //防止子Fragment 获取getActivity()为null
        //activity = (Activity) context;
        fragmentActivity = (FragmentActivity) context;

        Log.e(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
        Log.d(TAG1, "isHidden() = " + isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        rootView = inflater.inflate(setFragmentLayoutID(), container, false);
        initData();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated");
        isViewCreated = true;
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        //解决第一个fragment 起始不加载数据
        //ViewPager有预加载机制，默认左右两侧的tab页都会预加载并回调setUserVisibleHint方法，
        //只有当fragment的view加载完成后，才可以进行数据加载。
        //所以在此onStart()方法中调用setUserVisibleHint，此时view已经创建完毕，进行数据获取。
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
        //当Fragment销毁时，重置变量状态
        resetState();
    }

    private void resetState() {
        isFirst = true;
        isViewCreated = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged : hidden = " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint : isVisibleToUser = " + isVisibleToUser);

        if (getUserVisibleHint()) {
            //如果此时该fragment可见，则进行数据加载
            lazyLoadData();
        }
    }

    /**
     * 懒加载
     * 只有当第一次进入fragment并且UI已创建完成才进行数据刷新
     */
    public void lazyLoadData() {
        Log.e(TAG, "isFirst = " + isFirst);
        if (!isFirst || !isViewCreated) {
            return;
        }
        isFirst = false;
        //刷新数据
        refreshData();
    }

    //相关数据初始化
    public abstract void initData();

    //刷新数据
    public abstract void refreshData();

    //初始化View 控件
    public abstract void initView();

    //返回fragment绑定的layout ID
    public abstract int setFragmentLayoutID();


}
