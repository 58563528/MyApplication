package com.example.myinterview.myprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;

import com.example.myinterview.database.SQLiteHelper;

/**
 * 内容提供者
 * ContentProvider是不同应用程序之间进行数据交换的标准API，
 * ContentProvide以Uri的形式对外提供数据，允许其他应用访问和修改数据；
 * 其他应用使用ContentResolve根据Uri进行访问操作指定的数据。
 */
public class MyContentProvider extends ContentProvider {

    public static final String AUTOHORITY = "com.example.myinterview.myprovider";

    public static final int Person_DIR = 0;
    public static final int Person_ITEM = 1;
    public static final int User_DIR = 2;
    public static final int User_ITEM = 3;

    private Context mContext;
    private SQLiteHelper dbHelper = null;


    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTOHORITY, "person", Person_DIR);
        mMatcher.addURI(AUTOHORITY, "person/#", Person_ITEM);
        mMatcher.addURI(AUTOHORITY, "user", User_DIR);
        mMatcher.addURI(AUTOHORITY, "user#", User_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        dbHelper = new SQLiteHelper(mContext, 1);
        return true;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //根据URI匹配 URI_CODE,从而匹配ContentProvider中相应的表名
        String table = getTableName(uri);
        //向该表添加数据
        long newId = db.insert(table, null, values);
        Uri uriReturn = Uri.parse("content://" + AUTOHORITY + "/" + table + "/" + newId);

        //当该URI的ContentProvider数据发生变化时，通知外界，即访问该ContentProvider数据的访问者
        mContext.getContentResolver().notifyChange(uri, null);

        //返回一个用于表示该条新纪录的URI
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (mMatcher.match(uri)) {
            case Person_DIR:
                deleteRows = db.delete("person", selection, selectionArgs);
                break;
            case Person_ITEM:
                String personId = uri.getPathSegments().get(1);
                deleteRows = db.delete("person", "id = ?", new String[]{personId});
                break;
            case User_DIR:
                deleteRows = db.delete("user", selection, selectionArgs);
                break;
            case User_ITEM:
                String userId = uri.getPathSegments().get(1);
                deleteRows = db.delete("user", "id = ?", new String[]{userId});
                break;
        }

        //当该URI的ContentProvider数据发生变化时，通知外界，即访问该ContentProvider数据的访问者
        mContext.getContentResolver().notifyChange(uri, null);

        //返回被删除的行数
        return deleteRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (mMatcher.match(uri)) {
            case Person_DIR:
                deleteRows = db.update("person", values, selection, selectionArgs);
                break;
            case Person_ITEM:
                String personId = uri.getPathSegments().get(1);
                deleteRows = db.update("person", values, "id = ?", new String[]{personId});
                break;
            case User_DIR:
                deleteRows = db.update("user", values, selection, selectionArgs);
                break;
            case User_ITEM:
                String userId = uri.getPathSegments().get(1);
                deleteRows = db.update("user", values, "id = ?", new String[]{userId});
                break;
        }
        
        //当该URI的ContentProvider数据发生变化时，通知外界，即访问该ContentProvider数据的访问者
        mContext.getContentResolver().notifyChange(uri, null);

        return deleteRows;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        switch (mMatcher.match(uri)) {
            case Person_DIR:
                cursor = db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case Person_ITEM:
                String personId = uri.getPathSegments().get(1);
                cursor = db.query("person", projection, "id = ?", new String[]{personId}, null, null, sortOrder);
                break;
            case User_DIR:
                cursor = db.query("user", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case User_ITEM:
                String userId = uri.getPathSegments().get(1);
                cursor = db.query("user", projection, "id = ?", new String[]{userId}, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String mMIME = null;
        switch (mMatcher.match(uri)) {
            case Person_DIR:
                mMIME = "vnd.android.cursor.dir/vnd.com.example.myinterview.myprovider.person";
                break;
            case Person_ITEM:
                mMIME = "vnd.android.cursor.item/vnd.com.example.myinterview.myprovider.person";
                break;
            case User_DIR:
                mMIME = "vnd.android.cursor.dir/vnd.com.example.myinterview.myprovider.user";
                break;
            case User_ITEM:
                mMIME = "vnd.android.cursor.item/vnd.com.example.myinterview.myprovider.user";
                break;
        }
        return mMIME;
    }


    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case Person_DIR:
            case Person_ITEM:
                tableName = SQLiteHelper.PERSON_TABLE_NAME;
                break;
            case User_DIR:
            case User_ITEM:
                tableName = SQLiteHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
