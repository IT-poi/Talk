package com.cuit.talk.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cuit.talk.db.TalkOpenHelper;
import com.cuit.talk.entity.Group;
import com.cuit.talk.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inori on 16/10/12.
 */

public class GroupDao {
    private static GroupDao gropDao;

    private SQLiteDatabase database;

    private PersonDao personDao;

    private Context context;

    /**
     * 将构造函数私有化,防止通过new方式获取实例
     * @param context
     */
    private GroupDao(Context context){
        TalkOpenHelper dbHelper = new TalkOpenHelper(context,
                TalkOpenHelper.DB_NAMW, null, TalkOpenHelper.VERSION);
        database = dbHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * 单例模式 获取groupDao唯一实例
     * @param context
     * @return groupDao
     */
    public synchronized static GroupDao getInsetance(Context context){
        if (gropDao == null){
            gropDao = new GroupDao(context);
        }
        return gropDao;
    }

    /**
     * 通过person的id获取该用户的所有好友分组及好友信息
     * @param personId 用户id
     * @return 好友分组列表(包含好友信息列表)
     */
    public List<Group> queryGroupListByPersonId(int personId){
        //查询当前用户的所有的好友列表
        String groupSql = "select * from group where person_id = ?";
        //查询当前列表下的所有好友
        String friendSql = "select * from friend where group_id = ?";
        //提供PersonId数据
        String[] personIdValues = new String[]{String.valueOf(personId)};

        personDao = PersonDao.getInsetance(context);

        Cursor cursor = database.rawQuery(groupSql,personIdValues);
        List<Group> groups = null;
        if (cursor.moveToFirst()){
            groups = new ArrayList<Group>();
            do {
                Group group = new Group();
                group.setId(cursor.getInt(cursor.getColumnIndex("id")));
                group.setGroupName(cursor.getString(cursor.getColumnIndex("group_name")));
                group.setPersonId(cursor.getInt(cursor.getColumnIndex("personId")));

                String[] groudIdValue = new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("id")))};
                List<Person> personList = null;
                Cursor perCursor = database.rawQuery(friendSql,groudIdValue);
                if (perCursor.moveToFirst()){
                    personList = new ArrayList<Person>();
                    do {
                        Person person = personDao.queryPersonById(cursor.getInt(cursor.getColumnIndex("person_id")));
                        personList.add(person);
                    }while(perCursor.moveToNext());
                }
                group.setPersonsList(personList);

                groups.add(group);
            }while(cursor.moveToNext());
        }
        return groups;
    }
}
