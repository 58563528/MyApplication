package com.example.myinterview.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myinterview.R;
import com.example.myinterview.database.activity.DBMainActivity;
import com.example.myinterview.myfragment.activity.Main2Activity;
import com.example.myinterview.myprovider.activity.CPMainActivity;
import com.example.myinterview.service1.ServiceActivity;
import com.example.myinterview.service2.ServiceActivity2;
import com.example.myinterview.service3.ServiceActivity3;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void toMain2Activity(View view) {
        jumpActivity(Main2Activity.class);
    }
}
