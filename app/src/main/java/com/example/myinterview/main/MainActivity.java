package com.example.myinterview.main;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myinterview.R;
import com.example.myinterview.database.activity.DBMainActivity;
import com.example.myinterview.myfragment.activity.Main2Activity;
import com.example.myinterview.myprovider.activity.CPMainActivity;
import com.example.myinterview.service1.ServiceActivity;
import com.example.myinterview.service2.ServiceActivity2;
import com.example.myinterview.service3.ServiceActivity3;
import com.example.myinterview.service4.ServiceActivity4;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Ekko";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");
    }

    public void jumpActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void toServiceActivity2(View view) {
        jumpActivity(ServiceActivity2.class);
    }

    public void toServiceActivity3(View view) {
        jumpActivity(ServiceActivity3.class);
    }

    public void toDBMainActivity(View view) {
        jumpActivity(DBMainActivity.class);
    }

    public void toCPMainActivity(View view) {
        jumpActivity(CPMainActivity.class);
    }

    public void toServiceActivity(View view) {
        jumpActivity(ServiceActivity.class);
    }

    public void toIntentServiceActivity(View view) {
        jumpActivity(ServiceActivity4.class);
    }

    public void toMain2Activity(View view) {
        jumpActivity(Main2Activity.class);
    }

    public void toMySelf(View view) {
        jumpActivity(MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }
}
