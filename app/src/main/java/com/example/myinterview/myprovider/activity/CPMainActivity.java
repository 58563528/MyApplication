package com.example.myinterview.myprovider.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myinterview.R;
import com.example.myinterview.database.activity.DeleteDialog;
import com.example.myinterview.database.activity.InsertDialog;
import com.example.myinterview.database.ListAdapter;
import com.example.myinterview.common.model.Person;
import com.example.myinterview.database.activity.UpdateDialog;
import com.example.myinterview.myprovider.MyContentObsever;

import java.util.ArrayList;
import java.util.List;

public class CPMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Ekko";

    private static final int INSERT_REQUESTCODE = 1;
    private static final int INSERT_RESULTCODE = 11;
    private static final int DELETE_REQUESTCODE = 2;
    private static final int DELETE_RESULTCODE = 22;
    private static final int UPDATE_REQUESTCODE = 3;
    private static final int UPDATE_RESULTCODE = 33;

    private ListAdapter listAdapter;
    public static final String AUTOHORITY = "com.example.myinterview.myprovider";

    MyContentObsever myContentObsever;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpmain);


        Button insertPersonBtn = (Button) findViewById(R.id.person_insert);
        Button deletePersonBtn = (Button) findViewById(R.id.person_delete);
        Button updatePersonBtn = (Button) findViewById(R.id.person_update);
        Button queryPersonBtn = (Button) findViewById(R.id.person_query);
        text = (TextView) findViewById(R.id.change_text);
        insertPersonBtn.setOnClickListener(this);
        deletePersonBtn.setOnClickListener(this);
        updatePersonBtn.setOnClickListener(this);
        queryPersonBtn.setOnClickListener(this);


        listAdapter = new ListAdapter(new ArrayList<Person>(), this);
        ListView listView = (ListView) findViewById(R.id.list_data2);
        listView.setAdapter(listAdapter);

        //注册ContentObsever 传入Context 和 Handler
        Uri uri = Uri.parse("content://com.example.myinterview.myprovider/person");
        myContentObsever = new MyContentObsever(this, handler);
        getContentResolver().registerContentObserver(uri, true, myContentObsever);

    }

    //主线程 Handler 更新UI
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            text.setText(msg.obj.toString());
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //取消注册 ContentObserver
        getContentResolver().unregisterContentObserver(myContentObsever);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_insert:
                //case R.id.user_insert:
                insert();
                break;
            case R.id.person_delete:
                // case R.id.user_delete:
                delete();
                break;
            case R.id.person_update:
                //case R.id.user_update:
                update();
                break;
            case R.id.person_query:
                //case R.id.user_query:
                query();
                break;
            default:
                break;
        }
    }

    private void insert() {
        Intent intent = new Intent(this, InsertDialog.class);
        startActivityForResult(intent, INSERT_REQUESTCODE);
    }

    private void delete() {
        Intent intent = new Intent(this, DeleteDialog.class);
        startActivityForResult(intent, DELETE_REQUESTCODE);
    }

    private void update() {
        Intent intent = new Intent(this, UpdateDialog.class);
        startActivityForResult(intent, UPDATE_REQUESTCODE);
    }

    private void query() {
        Uri uri = Uri.parse("content://" + AUTOHORITY + "/person");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        List<Person> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int age = cursor.getInt(cursor.getColumnIndex("age"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));

                Person person = new Person(id, name, age, sex);
                list.add(person);
            } while (cursor.moveToNext());
            cursor.close();
        }

        loadData(list);
    }

    private void loadData(List<Person> list) {
        listAdapter.setList(list);
        listAdapter.notifyDataSetChanged();
    }

    private Cursor queryPersonById(Uri uri) {
        ContentResolver resolver = getContentResolver();
        return resolver.query(uri, null, null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case INSERT_REQUESTCODE:
                if (resultCode == INSERT_RESULTCODE) {
                    if (data != null) {
                        ContentResolver resolver = getContentResolver();
                        Person person = (Person) data.getSerializableExtra("person");
                        Uri uri = Uri.parse("content://" + AUTOHORITY + "/person");
                        ContentValues values = new ContentValues();
                        values.put("name", person.getName());
                        values.put("age", person.getAge());
                        values.put("sex", person.getSex());
                        resolver.insert(uri, values);

                        Toast.makeText(this, "Insert Success!", Toast.LENGTH_SHORT).show();
                        query();
                    } else {
                        Toast.makeText(this, "Insert Cancel!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case DELETE_REQUESTCODE:
                if (resultCode == DELETE_RESULTCODE) {
                    if (data != null) {
                        int id = data.getIntExtra("id", -1);
                        if (id != -1) {
                            ContentResolver resolver = getContentResolver();
                            Uri uri = Uri.parse("content://" + AUTOHORITY + "/person/" + id);
                            if (queryPersonById(uri).getCount() != 0) {
                                resolver.delete(uri, null, null);
                                Toast.makeText(this, "Delete Success!", Toast.LENGTH_SHORT).show();
                                query();
                            } else {
                                Toast.makeText(this, "Delete Fail! id " + id + " not exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Delete Fail!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(this, "Delete Cancel!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case UPDATE_REQUESTCODE:
                if (resultCode == UPDATE_RESULTCODE) {
                    if (data != null) {
                        Person person = (Person) data.getSerializableExtra("person");
                        int id = person.getId();
                        ContentResolver resolver = getContentResolver();
                        Uri uri = Uri.parse("content://" + AUTOHORITY + "/person/" + id);
                        if (queryPersonById(uri).getCount() != 0) {
                            ContentValues values = new ContentValues();
                            values.put("name", person.getName());
                            values.put("age", person.getAge());
                            values.put("sex", person.getSex());
                            resolver.update(uri, values, null, null);
                            Toast.makeText(this, "Update Success!", Toast.LENGTH_SHORT).show();
                            query();
                        } else {
                            Toast.makeText(this, "Update Fail! id " + id + " not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Update Cancel!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }

    }


}
