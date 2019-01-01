package com.example.myinterview.service2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myinterview.R;

import java.lang.ref.WeakReference;

/**
 * Service - Activity 通信  实现ServiceConnection接口方式
 */
public class ServiceActivity2 extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String TAG = "Ekko";
    private TextView textView;
    private boolean isBind = false;
    private int TransforData;
    MyService2.MyBinder binder = null;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);

        handler = new MyHandler(this);

        TransforData = 0;
        textView = (TextView) findViewById(R.id.textView);
        Button bindBtn = (Button) findViewById(R.id.bind_service2);
        Button unBindBtn = (Button) findViewById(R.id.unbind_service2);
        Button clearBt = (Button) findViewById(R.id.clear);
        bindBtn.setOnClickListener(this);
        unBindBtn.setOnClickListener(this);
        clearBt.setOnClickListener(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //获取binder对象
        binder = (MyService2.MyBinder) service;

        //向Service传递数据 TransforData
        binder.TransferData(TransforData);
        //获取从Service传递的MyService对象
        MyService2 myService2 = binder.getService();
        //接口回调 监控Service中的数据变化 并在handler中更新UI
        myService2.setCallback(new MyService2.Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_service2:
                if (!isBind) {
                    isBind = true;
                    //服务绑定后，会调用 onServiceConnected
                    Intent bindIntent = new Intent(this, MyService2.class);
                    bindService(bindIntent, this, BIND_AUTO_CREATE);
                    Toast.makeText(this, "Bind Service Success!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.unbind_service2:
                if (isBind) {
                    //从Service中获取data数值
                    TransforData = binder.getData();
                    unbindService(this);
                    isBind = false;
                    Toast.makeText(this, "unBind Service Success!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.clear:
                if (!isBind) {
                    TransforData = 0;
                    textView.setText("0");
                }
                break;
            default:
                break;
        }
    }

    /*
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在此处更新UI
            textView.setText(msg.obj.toString());
        }
    };*/
    private static class MyHandler extends Handler {
        WeakReference<ServiceActivity2> mWeakReference;

        MyHandler(ServiceActivity2 activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ServiceActivity2 activity = mWeakReference.get();
            if (activity != null) {
                //在此处更新UI
                activity.textView.setText(msg.obj.toString());
            }
        }
    }


}
