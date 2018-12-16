package com.example.myinterview.database.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.myinterview.R;
import com.example.myinterview.common.model.Person;

public class InsertDialog extends Activity implements View.OnClickListener {

    //插入请求代码 大于0
    private static final int INSERT_RESULTCODE = 11;
    //姓名控件
    private EditText nameView;
    //年龄控件
    private EditText ageView;
    //性别控件
    private EditText sexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_insert_dialog);

        nameView = (EditText) findViewById(R.id.insert_edit_name);
        ageView = (EditText) findViewById(R.id.insert_edit_age);
        sexView = (EditText) findViewById(R.id.insert_edit_sex);

        Button confirmBtn = (Button) findViewById(R.id.insert_confirm);
        Button cancelBtn = (Button) findViewById(R.id.insert_cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

    }

    private void confirm() {
        String name = String.valueOf(nameView.getText());
        int age = Integer.valueOf(String.valueOf(ageView.getText()));
        String sex = String.valueOf(sexView.getText());
        Person person = new Person(name, age, sex);
        Intent intent = new Intent();
        intent.putExtra("person", person);
        setResult(INSERT_RESULTCODE, intent);
        finish();
    }

    private void cancel() {
        setResult(INSERT_RESULTCODE);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.insert_confirm:
                confirm();
                break;
            case R.id.insert_cancel:
                cancel();
                break;
            default:
                break;
        }

    }
}
