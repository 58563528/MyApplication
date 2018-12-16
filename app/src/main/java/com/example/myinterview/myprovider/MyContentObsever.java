package com.example.myinterview.myprovider;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * 内容观察者   观察所监听Uri 引起ContentProvider中的数据变化
 */
public class MyContentObsever extends ContentObserver {

    private Context context;
    private Handler handler;


    public MyContentObsever(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        Toast.makeText(context, "database has changed!", Toast.LENGTH_LONG).show();
        Log.d("Ekko", "uri = null");
        Message msg = new Message();
        msg.obj = "database has changed";
        handler.sendMessage(msg);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Toast.makeText(context, "database has changed!", Toast.LENGTH_LONG).show();
        Log.d("Ekko", "uri =" + uri);
        Message msg = new Message();
        msg.obj = "database has changed";
        handler.sendMessage(msg);
    }


}
