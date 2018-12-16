package com.example.myinterview.service3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService3 extends Service {

    private boolean isConnected = false;
    public static final String COUNTER = "data";
    public static final String ACTION_NAME = "com.example.myinterview.service3.COUNTER_ACTION";

    private int data;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //从Activity获取data
        data = intent.getIntExtra(COUNTER, 0);

        final Intent mIntent = new Intent();
        mIntent.setAction(ACTION_NAME);
        isConnected = true;
        //开启一个线程，对数据进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isConnected) {
                        //耗时操作：数据处理并保存，向Activity发送广播
                        mIntent.putExtra(COUNTER, data);
                        sendBroadcast(mIntent);
                        data++;
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isConnected = false;
    }
}
