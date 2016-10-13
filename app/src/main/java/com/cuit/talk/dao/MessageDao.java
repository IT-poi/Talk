package com.cuit.talk.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cuit.talk.db.TalkOpenHelper;
import com.cuit.talk.entity.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 落叶刻痕 on 2016/10/11.
 */

public class MessageDao {

    private static MessageDao weatherDao;

    private SQLiteDatabase database;

    public MessageDao(Context context) {
        TalkOpenHelper dbHelper = new TalkOpenHelper(context, TalkOpenHelper.DB_NAMW, null, TalkOpenHelper.VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 获取messageDao实例，只会得到唯一一个实例（单例模式）
     * @param context
     * @return MessageDao实例
     */
    public synchronized static MessageDao getInsetance (Context context){
        if (weatherDao==null){
            weatherDao = new MessageDao(context);
        }
        return weatherDao;
    }

    /**
     * 通过发送消息者id和接收消息者id查询和好友的所有聊天记录
     * @param sendId 发送者id
     * @param receiveId 接受者id
     * @return 消息list
     */
    public List<Message> queryMessageAll(int sendId, int receiveId){
        List<Message> list = null;
        String sql = "select * from message where send_id = ? and receive_id = ?";
        Cursor cursor = database.rawQuery(sql,
                new String[]{String.valueOf(sendId), String.valueOf(receiveId)});
        if(cursor.moveToFirst()){
            list = new ArrayList<Message>();
            do{
                Message message = new Message();
                message.setId(cursor.getInt(cursor.getColumnIndex("id")));
                message.setSendId(cursor.getInt(cursor.getColumnIndex("send_id")));
                message.setReceiveId(cursor.getInt(cursor.getColumnIndex("receive_id")));
                message.setContent(cursor.getString(cursor.getColumnIndex("content")));
                message.setSendTime(cursor.getString(cursor.getColumnIndex("send_time")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 通过接受消息插入到数据库
     * @param message 发送的消息
     */
    public void insertMessage(Message message){

        String sql = "insert into message values(?, ?, ?, ?, ?)";
        String[] values = new String[]{String.valueOf(message.getId()),
                String.valueOf(message.getSendId()), String.valueOf(message.getSendId()),
                String.valueOf(message.getReceiveId()), message.getContent(), message.getSendTime()};
        database.execSQL(sql,values);
    }


    /**
     * 通过消息id删除message
     * @param id 消息的id
     */
    public void deleteMessageBySendId(int id){
        String sql="delete from message where id=?";
        database.execSQL(sql,new String[]{String.valueOf(id)});
    }
}
