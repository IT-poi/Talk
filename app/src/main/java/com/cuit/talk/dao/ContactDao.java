package com.cuit.talk.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cuit.talk.db.TalkOpenHelper;
import com.cuit.talk.entity.Contact;
import com.cuit.talk.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rice on 16-10-17.
 */
public class ContactDao {
    private Context context;
    private static ContactDao contactDao;
    private SQLiteDatabase sqLiteDatabase;
    private PersonDao personDao;

    /**
     * 单例模式获得ContactDao对象
     * @param context
     * @return ContactDao对象
     */
    public synchronized static ContactDao getContactDao(Context context){
        if(contactDao==null){
            contactDao = new ContactDao(context);
        }
        return contactDao;
    }

    private ContactDao(Context context){
        Log.d("ContactDao","ContactDao");
        this.context = context;
        TalkOpenHelper dbHelper = new TalkOpenHelper(context,
                TalkOpenHelper.DB_NAME, null, TalkOpenHelper.VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }
    /**
     * 该方法是返回所有最近联系人的列表
     * @param personId 自己的id
     * @return 所有最近联系人的列表
     */
    public List<Contact> queryAllContactByPersonId(int personId){
        List<Contact> personList = new ArrayList<Contact>();
        Contact contact = null;
        String sql = "select friend_id from contact where person_id = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{String.valueOf(personId)});
        if(cursor.moveToNext()){
            do {
                contact = new Contact();
                contact.setPersonId(personId);
                contact.setFriendId(cursor.getInt(cursor.getColumnIndex("friend_id")));
                personList.add(contact);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return personList;
    }

    /**
     * 该方法是删除自己与某个好友的联系
     * @param personId 自己的id
     * @param friendId 好友的id
     */
    public void deleteContact(int personId,int friendId){
        String sql = "delete from contact where friend_id = ? and person_id = ?";
        String[] values = new String[]{String.valueOf(friendId),String.valueOf(personId)};
        sqLiteDatabase.execSQL(sql,values);
    }

    /**
     * 该方法是添加自己与某个好友的联系
     * @param personId 自己的id
     * @param friendId 好友的id
     */
    public void addContact(int personId,int friendId){
        String sql = "insert into contact(person_id,friend_id) values(?,?)";
        String[] values = new String[]{String.valueOf(personId),String.valueOf(friendId)};
        sqLiteDatabase.execSQL(sql,values);
    }
}
