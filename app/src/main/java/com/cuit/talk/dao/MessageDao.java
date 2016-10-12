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
    public static final String DB_NAMW = "talk";

    public static final int VERSION = 1;

    private static MessageDao weatherDao;

    private SQLiteDatabase database;

    public MessageDao(Context context) {
        TalkOpenHelper dbHelper = new TalkOpenHelper(context, DB_NAMW, null, VERSION);
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
        return list;
    }

    public int insertMessage(Message message){

        String sql = "insert into message values(?, ?, ?, ?, ?)";
        String[] vaules = new String[]{String.valueOf(message.getId()),
                String.valueOf(message.getSendId()), String.valueOf(message.getSendId()),
                String.valueOf(message.getReceiveId()), message.getContent(), message.getSendTime()};

        return 0;
    }
}
