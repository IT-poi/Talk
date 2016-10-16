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
            "id integer primary key, " +
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
            "id integer primary key, " +
            "send_id integer, " +
            "receive_id integer, " +
            "content text, " +
            "send_time text)";
    //创建分组数据表的sql
    private final String create_groups = "create table friend_groups(" +
            "id integer primary key, " +
            "group_name text, " +
            "person_id integer, " +
            "create_time text)";
    //创建好友数据表的sql
    private final String create_friend = "create table friend(" +
            "id integer primary key, " +
            "person_id integer, " +
            "group_id integer, " +
            "friend_id integer, " +
            "create_time text)";

    public TalkOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_person);
        sqLiteDatabase.execSQL(create_friend);
        sqLiteDatabase.execSQL(create_message);
        sqLiteDatabase.execSQL(create_groups);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
