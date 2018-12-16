package com.example.myinterview.service3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myinterview.R;

/**
 * Service - Activity 通信  广播实现方式
 */
public class ServiceActivity3 extends AppCompatActivity implements View.OnClickListener {

    private int TransforData;
    private TextView textView;
    private Intent mIntent;
    private MyReceiver myReceiver;
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service3);

        TransforData = 0;
        textView = (TextView) findViewById(R.id.textView);
        Button startBtn = (Button) findViewById(R.id.mstart);
        Button pauseBtn = (Button) findViewById(R.id.pause);
        Button clearBt = (Button) findViewById(R.id.clear);
        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        clearBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mstart:
                if (!isBind) {
                    isBind = true;
                    //向Service传递data
                    mIntent.putExtra(MyService3.COUNTER, TransforData);
                    startService(mIntent);
                    Toast.makeText(this, "Start!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pause:
                //停止服务
                stopService(mIntent);
                isBind = false;
                break;
            case R.id.clear:
                if (!isBind) {
                    TransforData = 0;
                    textView.setText("0");
                    Toast.makeText(this, "Pause!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //获取从Service中传来的data
                    TransforData = intent.getIntExtra(MyService3.COUNTER, 0);
                    //更新UI
                    textView.setText(String.valueOf(TransforData));
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mIntent = new Intent(this, MyService3.class);
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService3.ACTION_NAME);
        //注册广播
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        stopService(mIntent);
        super.onDestroy();
    }
}
