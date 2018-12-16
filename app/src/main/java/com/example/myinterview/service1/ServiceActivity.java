package com.example.myinterview.service1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cptestapp.MyAIDLInterface;
import com.example.myinterview.R;

/**
 * ServiceActivity 和 远程Service进行通信
 * 通过AIDL接口  Service在远程或者其他app中
 */
public class ServiceActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private static final String TAG = "Ekko";
    private TextView textView;
    private boolean isBind = false;
    private int TransforData;
    //定义AIDL接口变量
    private MyAIDLInterface myAIDLInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service2);

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
        //将binder对象转换成了 MyAIDLInterface接口对象
        myAIDLInterface = MyAIDLInterface.Stub.asInterface(service);

        try {
            //向Service传递数据 TransforData
            myAIDLInterface.TransferData(TransforData);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

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
                    Intent bindIntent = new Intent("com.example.cptestapp.MyAIDLInterface");
                    bindIntent.setPackage("com.example.cptestapp");
                    bindService(bindIntent, this, BIND_AUTO_CREATE);


                    Toast.makeText(this, "Bind Service Success!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.unbind_service2:
                if (isBind) {
                    try {
                        //从Service中获取data数值
                        TransforData = myAIDLInterface.getData();

                        Message msg = new Message();
                        msg.obj = TransforData;
                        handler.sendMessage(msg);

                        unbindService(this);
                        isBind = false;
                        Toast.makeText(this, "unBind Service Success!", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在此处更新UI
            textView.setText(msg.obj.toString());
        }
    };
}
