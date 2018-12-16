package com.example.myinterview.service2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService2 extends Service {

    private boolean isConnected = false;
    private Callback callback;
    public int data = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {

        //向Activity返回MyService2实例
        MyService2 getService() {
            return MyService2.this;
        }

        //获取从Activity传来的数据
        void TransferData(int mData) {
            data = mData;
        }

        //将data数值传递给Activity
        int getData() {
            return data;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        isConnected = true;
        //开启一个线程，对数据进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConnected) {
                    if (callback != null) {
                        callback.onDataChange(data + "");
                    }
                    data++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback() {
        return callback;
    }

    public interface Callback {
        void onDataChange(String data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isConnected = false;
    }
}
