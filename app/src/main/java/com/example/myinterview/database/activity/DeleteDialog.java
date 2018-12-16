package com.example.myinterview.database.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.myinterview.R;

public class DeleteDialog extends Activity implements View.OnClickListener {

    //插入请求代码
    private static final int DELETE_RESULTCODE = 22;
    //id控件
    private EditText idView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_dialog);

        idView = (EditText) findViewById(R.id.delete_edit_id);
        Button confirmBtn = (Button) findViewById(R.id.delete_confirm);
        Button cancelBtn = (Button) findViewById(R.id.delete_cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void confirm() {
        int id = Integer.valueOf(String.valueOf(idView.getText()));
        Intent intent = new Intent();
        intent.putExtra("id", id);
        setResult(DELETE_RESULTCODE, intent);
        finish();
    }

    private void cancel() {
        setResult(DELETE_RESULTCODE);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_confirm:
                confirm();
                break;
            case R.id.delete_cancel:
                cancel();
                break;
            default:
                break;

        }
    }
}
