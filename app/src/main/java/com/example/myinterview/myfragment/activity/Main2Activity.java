package com.example.myinterview.myfragment.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myinterview.R;
import com.example.myinterview.myfragment.BottomNavigationViewHelper;
import com.example.myinterview.myfragment.fragment.BaseFragment;
import com.example.myinterview.myfragment.fragment.navigation.CarFragment;
import com.example.myinterview.myfragment.fragment.navigation.MusicFragment;
import com.example.myinterview.myfragment.fragment.navigation.NavigateFragment;
import com.example.myinterview.myfragment.fragment.navigation.SettingFragment;
import com.example.myinterview.myfragment.fragment.navigation.ToysFragment;

import java.util.ArrayList;


public class Main2Activity extends FragmentActivity {

    TextView toolbar_title;
    private static final String TAG = "Main2Activity1";
    private NavigateFragment navigateFragment;
    private MusicFragment musicFragment;
    private CarFragment carFragment;
    private SettingFragment settingFragment;
    private ToysFragment toysFragment;
    private BaseFragment currentFragment;

    private static final String NAVIGATATE = "NavigateFragment";
    private static final String MUSIC = "MusicFragment";
    private static final String CAR = "CarFragment";
    private static final String SETTING = "SettingFragment";
    private static final String TOYS = "ToysFragment";

    private static final String CURRENTFRAGMENT = "navigateFragment";

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.tab_1);

        //底部 Tab页
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //当BottomNavigationView的item个数大于3时，取消自带动画效果。
        //利用反射
        BottomNavigationViewHelper.disableShiftMode(navigation);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            //正常启动
            initHomeFragment();
        } else {
            //解决重叠问题
            navigateFragment = (NavigateFragment) fragmentManager.findFragmentByTag(getString(R.string.tab_1));
            musicFragment = (MusicFragment) fragmentManager.findFragmentByTag(getString(R.string.tab_2));
            carFragment = (CarFragment) fragmentManager.findFragmentByTag(getString(R.string.tab_3));
            settingFragment = (SettingFragment) fragmentManager.findFragmentByTag(getString(R.string.tab_4));
            toysFragment = (ToysFragment) fragmentManager.findFragmentByTag(getString(R.string.tab_5));

            //navigateFragment = (NavigateFragment) fragmentManager.getFragment(savedInstanceState, NAVIGATATE);
            //musicFragment = (MusicFragment) fragmentManager.getFragment(savedInstanceState, MUSIC);
            //carFragment = (CarFragment) fragmentManager.getFragment(savedInstanceState, CAR);
            //settingFragment = (SettingFragment) fragmentManager.getFragment(savedInstanceState, SETTING);
            //toysFragment = (ToysFragment) fragmentManager.getFragment(savedInstanceState, TOYS);

            Log.d(TAG, "navigateFragment = " + navigateFragment);
            Log.d(TAG, "musicFragment = " + musicFragment);
            Log.d(TAG, "carFragment = " + carFragment);
            Log.d(TAG, "settingFragment = " + settingFragment);
            Log.d(TAG, "toysFragment = " + toysFragment);

            currentFragment = (BaseFragment) fragmentManager.getFragment(savedInstanceState, CURRENTFRAGMENT);
            Log.d(TAG, "currentFragment = " + currentFragment);
        }


    }

    //BottomNavigationView 底部Tab页 menu菜单监听
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Log.d(TAG, "onNavigationItemSelected");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar_title.setText(R.string.tab_1);
                    clickNavigationLayout(fragmentTransaction);
                    return true;
                case R.id.navigation_music:
                    toolbar_title.setText(R.string.tab_2);
                    clickMusicLayout(fragmentTransaction);
                    return true;
                case R.id.navigation_car:
                    toolbar_title.setText(R.string.tab_3);
                    clickCarLayout(fragmentTransaction);
                    return true;
                case R.id.navigation_setting:
                    toolbar_title.setText(R.string.tab_4);
                    clickSettingLayout(fragmentTransaction);
                    return true;
                case R.id.navigation_toys:
                    toolbar_title.setText(R.string.tab_5);
                    clickToysLayout(fragmentTransaction);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
//        if (navigateFragment != null) {
//            fragmentManager.putFragment(outState, NAVIGATATE, navigateFragment);
//        }
//        if (musicFragment != null) {
//            fragmentManager.putFragment(outState, MUSIC, musicFragment);
//        }
//        if (carFragment != null) {
//            fragmentManager.putFragment(outState, CAR, carFragment);
//        }
//        if (settingFragment != null) {
//            fragmentManager.putFragment(outState, SETTING, settingFragment);
//        }
//        if (toysFragment != null) {
//            fragmentManager.putFragment(outState, TOYS, toysFragment);
//        }
        //保存currentFragment
        fragmentManager.putFragment(outState, CURRENTFRAGMENT, currentFragment);

        super.onSaveInstanceState(outState);
    }

    private void initHomeFragment() {
        Log.d(TAG, "initHomeFragment");
        //设置默认显示的fragment
        if (navigateFragment == null) {
            navigateFragment = NavigateFragment.newInstance(getString(R.string.tab_1));
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!navigateFragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_content, navigateFragment, getString(R.string.tab_1));

        } else {
            fragmentTransaction.show(navigateFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();

        //addToList(navigateFragment);

        currentFragment = navigateFragment;

    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragmentsList.add(fragment);
        }
        Log.d(TAG, "fragmentsList size = " + fragmentsList.size());

    }

    //判定fragment是add 还是 hide
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment, String TAG1) {

        Log.d(TAG, "currentFragment = " + currentFragment);
        Log.d(TAG, "fragment = " + fragment);
        if (currentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            transaction.hide(currentFragment);
            transaction.add(R.id.fragment_content, fragment, TAG1);
            //addToList(fragment);
        } else {
            transaction.hide(currentFragment);
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();

        currentFragment.setUserVisibleHint(false);
        currentFragment = (BaseFragment) fragment;
        currentFragment.setUserVisibleHint(true);
    }


    private void clickNavigationLayout(FragmentTransaction fragmentTransaction) {
        Log.d(TAG, "clickNavigationLayout");
        if (navigateFragment == null) {
            navigateFragment = NavigateFragment.newInstance(getString(R.string.tab_1));
        }
        addOrShowFragment(fragmentTransaction, navigateFragment, getString(R.string.tab_1));
    }

    private void clickMusicLayout(FragmentTransaction fragmentTransaction) {
        Log.d(TAG, "clickMusicLayout");
        if (musicFragment == null) {
            musicFragment = MusicFragment.newInstance(getString(R.string.tab_2));
        }
        addOrShowFragment(fragmentTransaction, musicFragment, getString(R.string.tab_2));
    }

    private void clickCarLayout(FragmentTransaction fragmentTransaction) {
        Log.d(TAG, "clickCarLayout");
        if (carFragment == null) {
            carFragment = CarFragment.newInstance(getString(R.string.tab_3));
        }
        addOrShowFragment(fragmentTransaction, carFragment, getString(R.string.tab_3));
    }

    private void clickSettingLayout(FragmentTransaction fragmentTransaction) {
        Log.d(TAG, "clickSettingLayout");
        if (settingFragment == null) {
            settingFragment = SettingFragment.newInstance(getString(R.string.tab_4));
        }
        addOrShowFragment(fragmentTransaction, settingFragment, getString(R.string.tab_4));
    }

    private void clickToysLayout(FragmentTransaction fragmentTransaction) {
        Log.d(TAG, "clickSettingLayout");
        if (toysFragment == null) {
            toysFragment = ToysFragment.newInstance(getString(R.string.tab_5));
        }
        addOrShowFragment(fragmentTransaction, toysFragment, getString(R.string.tab_5));
    }
}
