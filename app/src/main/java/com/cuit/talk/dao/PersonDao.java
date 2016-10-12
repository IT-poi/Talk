package com.cuit.talk.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cuit.talk.db.TalkOpenHelper;
import com.cuit.talk.entity.Person;

/**
 * Created by inori on 16/10/12.
 */

public class PersonDao {
    private static PersonDao personDao;

    private SQLiteDatabase database;

    private PersonDao(Context context){
        TalkOpenHelper dbHelper = new TalkOpenHelper(context,
                TalkOpenHelper.DB_NAMW, null, TalkOpenHelper.VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 单例模式 获取PersonDao唯一实例
     * @param context
     * @return personDao
     */
    public synchronized static PersonDao getInsetance(Context context){
        if (personDao == null){
            personDao = new PersonDao(context);
        }
        return personDao;
    }

    /**
     * 通过id查询对应的person信息
     * @param personId 用户id
     * @return 用户信息
     */
    public Person queryPersonById(int personId){
        String sql = "select * from person where id = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(personId)});
        Person person = null;
        if (cursor.moveToFirst()){
            person = new Person();
            person.setId(cursor.getInt(cursor.getColumnIndex("id")));
            person.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            person.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            person.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
            person.setTruename(cursor.getString(cursor.getColumnIndex("truename")));
            person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
            person.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            person.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        }
        return person;
    }
}
