package com.example.myinterview.service4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myinterview.R;

public class ServiceActivity4 extends AppCompatActivity implements MyIntentService.UpdateUI {

    /**
     * 图片地址集合
     */
    private String url[] = {
            "https://img-blog.csdn.net/20160903083245762",
            "https://img-blog.csdn.net/20160903083252184",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083257871",
            "https://img-blog.csdn.net/20160903083311972",
            "https://img-blog.csdn.net/20160903083319668",
            "https://img-blog.csdn.net/20160903083326871"
    };

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service4);
        imageView = (ImageView) findViewById(R.id.image);

        Intent intent = new Intent(this, MyIntentService.class);
        for (int i = 0; i < 7; i++) {//循环启动任务
            intent.putExtra(MyIntentService.DOWNLOAD_URL, url[i]);
            intent.putExtra(MyIntentService.INDEX_FLAG, i);
            startService(intent);
        }
        MyIntentService.setUpdateUI(this);
    }

    //必须通过Handler去更新，该方法为异步方法，不可更新UI
    @Override
    public void updateUI(Message message) {
        mUIHandler.sendMessageDelayed(message, message.what * 1000);
    }

    private final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

}
