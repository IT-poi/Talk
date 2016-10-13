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

    /**
     * 将构造函数私有化,防止通过new的方法获取实例
     * @param context
     */
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

    /**
     * 向数据库中插入一行person数据
     * @param person 用户信息
     */
    public void insertPerson(Person person){
        String sql = "insert into person values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String[] values = new String[]{String.valueOf(person.getId()),person.getNumber(),
                person.getPassword(), person.getNickname(), person.getTruename(), person.getSex(), String.valueOf(person.getAge()),
                person.getPhone(), person.getAddress()};
        database.execSQL(sql, values);
    }

    /**
     * 根据传来的Person信息修改用户信息
     * @param person
     */
    public void updatePerson(Person person){
        String sql = "update person set password = ?, nickname, truename = ?, sex = ?, age = ?, phone = ?, address = ?" +
                "where id = ?";
        String[] values = new String[]{person.getPassword(), person.getNickname(), person.getTruename(),
                person.getSex(), String.valueOf(person.getAge()), person.getPhone(), person.getAddress(), String.valueOf(person.getId())};
        database.execSQL(sql, values);
    }

    /**
     * 根据用户id删除用户数据(一般不使用这个操作)
     * @param peronId
     */
    public void deletePersonById(int peronId){
        //删除用户资料信息sql
        String deletePersonSql = "delete from person where id = ?";
        String[] values = new String[]{String.valueOf(peronId)};
        //删除关联表中数据。。待实现,因为此方法一般不会使用到
        database.beginTransaction();//开启事物
        try{
            database.execSQL(deletePersonSql, values);
        }finally {
            database.endTransaction();//最后结束事物,有两种情况commit,rollback
        }
    }
}
