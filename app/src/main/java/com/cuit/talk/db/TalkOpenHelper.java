package com.cuit.talk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 落叶刻痕 on 2016/10/11.
 */

public class TalkOpenHelper extends SQLiteOpenHelper{

    public static final String DB_NAMW = "talk";

    public static final int VERSION = 1;

    //创建用户数据表的sql
    private final String create_person = "create table person(" +
            "id integer primary key autoincrement, " +
            "number text, " +
            "password text, " +
            "nickname text, " +
            "truename text, " +
            "sex text, " +
            "age integer, " +
            "phone text, " +
            "address text)";
    //创建消息数据表的sql
    private final String create_message = "create table message(" +
            "id integer primary key autoincrement, " +
            "send_id integer, " +
            "receive_id integer, " +
            "content text, " +
            "send_time text)";
    //创建分组数据表的sql
    private final String create_groups = "create table group(" +
            "id integer primary key autoincrement, " +
            "group_name text, " +
            "person_id integer)";
    //创建好友数据表的sql
    private final String create_friend = "create table friend(" +
            "id integer, " +
            "group_id integer)";

    public TalkOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_person);
        sqLiteDatabase.execSQL(create_groups);
        sqLiteDatabase.execSQL(create_friend);
        sqLiteDatabase.execSQL(create_message);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
