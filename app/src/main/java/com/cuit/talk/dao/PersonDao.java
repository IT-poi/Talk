package com.cuit.talk.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.cuit.talk.db.TalkOpenHelper;
import com.cuit.talk.entity.Friend;
import com.cuit.talk.entity.Person;

/**
 * Created by inori on 16/10/12.
 */

public class PersonDao {
    private static PersonDao personDao;

    private SQLiteDatabase database;

    private Context context;

    /**
     * 将构造函数私有化,防止通过new的方法获取实例
     * @param context
     */
    private PersonDao(Context context){
        this.context = context;
        TalkOpenHelper dbHelper = new TalkOpenHelper(context,
                TalkOpenHelper.DB_NAME, null, TalkOpenHelper.VERSION);
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
     * 通过number查询对应的person信息
     * @param personNumber 用户id
     * @return 用户信息
     */
    public Person queryPersonByNumber(String personNumber){
        String sql = "select * from person where number = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{personNumber});
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
    public Boolean insertPerson(Person person){
        String sql = "insert into person(number, password, nickname, truename, sex, age, phone, address) values(?, ?, ?, ?, ?, ?, ?, ?)";
        String[] values = new String[]{
                person.getNumber(),
                person.getPassword(),
                person.getNickname(),
                person.getTruename(),
                person.getSex(),
                String.valueOf(person.getAge()),
                person.getPhone(),
                person.getAddress()};
        try{
            database.execSQL(sql, values);
        }catch (SQLiteConstraintException e){
            return false;
        }
        return true;
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

    /**
     * 删除好友
     * @param personId 用户id
     * @param friendId 好友id
     */
    public void deleteFriendById(int personId, int friendId){
        String deleteFriendSql = "delete from friend where person_id = ? and friend_id = ?";
        String[] values = new String[]{String.valueOf(personId), String.valueOf(friendId)};
        //开启事物保证操作要么全部成功要么全部失败
        database.beginTransaction();
        try{
            database.execSQL(deleteFriendSql, values);
        }finally {
            //事物结束
            database.endTransaction();
        }
    }

    //TODO
    public boolean addFriend(Friend friend){
        String sql = "insert into friend(person_id, group_id, friend_id, create_time) values(?, ?, ?, ?)";
        String[] values = new String[] {String.valueOf(friend.getPersonId()),
                String.valueOf(friend.getGroupId()), String.valueOf(friend.getFriendId()), friend.getCreateTime()};
        try{
            database.execSQL(sql, values);
        }catch (SQLiteConstraintException e){
            return false;
        }
        return true;
    }
}
