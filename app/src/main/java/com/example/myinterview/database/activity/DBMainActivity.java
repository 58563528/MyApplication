package com.example.myinterview.database.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myinterview.R;
import com.example.myinterview.common.model.Person;
import com.example.myinterview.database.ListAdapter;
import com.example.myinterview.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite 数据库操作 增删改查
 */
public class DBMainActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteHelper db;
    private ListAdapter listAdapter;

    private static final int INSERT_REQUESTCODE = 1;
    private static final int INSERT_RESULTCODE = 11;
    private static final int DELETE_REQUESTCODE = 2;
    private static final int DELETE_RESULTCODE = 22;
    private static final int UPDATE_REQUESTCODE = 3;
    private static final int UPDATE_RESULTCODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbmain);

        db = new SQLiteHelper(this, 1);

        Button insertBtn = (Button) findViewById(R.id.btn_insert);
        Button deleteBtn = (Button) findViewById(R.id.btn_delete);
        Button updateBtn = (Button) findViewById(R.id.btn_update);
        Button queryBtn = (Button) findViewById(R.id.btn_query);
        insertBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);

        listAdapter = new ListAdapter(new ArrayList<Person>(), this);
        ListView listView = (ListView) findViewById(R.id.list_data);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
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
        List<Person> list = db.queryAllPerson();
        loadData(list);
    }

    private void loadData(List<Person> list) {
        listAdapter.setList(list);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case INSERT_REQUESTCODE:
                if (resultCode == INSERT_RESULTCODE) {
                    if (data != null) {
                        db.insertPerson((Person) data.getSerializableExtra("person"));
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
                            if (db.queryPersonById(id) != null) {
                                db.deletePersonById(id);
                                Toast.makeText(this, "Delete Success!", Toast.LENGTH_SHORT).show();
                                query();
                            } else {
                                Toast.makeText(this, "Delete Fail!\tid" + id + "not exist", Toast.LENGTH_SHORT).show();
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
                        if (db.queryPersonById(person.getId()) != null) {
                            db.updatePerson(person);
                            Toast.makeText(this, "Update Success!", Toast.LENGTH_SHORT).show();
                            query();
                        } else {
                            Toast.makeText(this, "Update Fail!\tid" + id + "not exist", Toast.LENGTH_SHORT).show();
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
