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

public class UpdateDialog extends Activity implements View.OnClickListener {
    //修改请求代码
    private static final int UPDATE_RESULTCODE = 33;
    //id控件
    private EditText idView;
    //姓名控件
    private EditText nameView;
    //年龄控件
    private EditText ageView;
    //性别控件
    private EditText sexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_dialog);

        idView = (EditText) findViewById(R.id.update_edit_id);
        nameView = (EditText) findViewById(R.id.update_edit_name);
        ageView = (EditText) findViewById(R.id.update_edit_age);
        sexView = (EditText) findViewById(R.id.update_edit_sex);

        Button confirmBtn = (Button) findViewById(R.id.update_confirm);
        Button cancelBtn = (Button) findViewById(R.id.update_cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void confirm() {
        int id = Integer.valueOf(String.valueOf(idView.getText()));
        String name = String.valueOf(nameView.getText());
        int age = Integer.valueOf(String.valueOf(ageView.getText()));
        String sex = String.valueOf(sexView.getText());
        Person person = new Person(id, name, age, sex);

        Intent intent = new Intent();
        intent.putExtra("person", person);
        setResult(UPDATE_RESULTCODE, intent);
        finish();
    }

    private void cancel() {
        setResult(UPDATE_RESULTCODE);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_confirm:
                confirm();
                break;
            case R.id.update_cancel:
                cancel();
                break;
            default:
                break;
        }
    }
}
