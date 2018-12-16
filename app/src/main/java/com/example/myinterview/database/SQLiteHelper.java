package com.example.myinterview.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myinterview.common.model.Person;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteHelper";
    private static final String DATABASE_NAME = "mydb.db";
    public static final String PERSON_TABLE_NAME = "person";
    public static final String USER_TABLE_NAME = "user";

    public SQLiteHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "数据库创建");
        String sql1 = "create table if not exists " + PERSON_TABLE_NAME +
                "(id integer Primary Key autoincrement," +
                "name varchar(20)," +
                "age integer," +
                "sex varchar(20))";
        String sql2 = "create table if not exists " + USER_TABLE_NAME +
                "(id integer Primary Key autoincrement," +
                "name varchar(20)," +
                "age integer," +
                "sex varchar(20))";
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "数据库更新");
    }

    //插入
    public void insertPerson(Person person) {
        Log.e(TAG, "插入");

        SQLiteDatabase db = getWritableDatabase();
        String sql = "insert into " + PERSON_TABLE_NAME + "(name,age,sex) values("
                + String.format("'%s'", person.getName()) + ","
                + person.getAge() + ","
                + String.format("'%s'", person.getSex()) +
                ");";
        //插入数据库
        db.execSQL(sql);

        db.close();
    }

    //更新
    public void updatePerson(Person person) {
        Log.e(TAG, "更新");
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update " + PERSON_TABLE_NAME + " set name = "
                + String.format("'%s'", person.getName())
                + ",age = " + person.getAge()
                + ",sex = " + String.format("'%s'", person.getSex())
                + "where id = " + person.getId();
        db.execSQL(sql);
        db.close();
    }

    //删除
    public void deletePersonById(int id) {
        Log.e(TAG, "删除");
        SQLiteDatabase db = getWritableDatabase();
        String sql = "id = ?";
        String wheres[] = {String.valueOf(id)};
        db.delete(PERSON_TABLE_NAME, sql, wheres);
        db.close();
    }

    //查询所有
    public List<Person> queryAllPerson() {
        List<Person> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + PERSON_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));

            Person person = new Person();
            person.setId(id);
            person.setName(name);
            person.setAge(age);
            person.setSex(sex);
            list.add(person);
        }
        cursor.close();
        db.close();
        return list;
    }

    //根据id查询
    public Person queryPersonById(int id) {
        Person person = null;
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"id", "name", "age", "sex"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(PERSON_TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            person = new Person();
            person.setId(cursor.getInt(cursor.getColumnIndex("id")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
        }
        cursor.close();
        db.close();
        return person;
    }
}
