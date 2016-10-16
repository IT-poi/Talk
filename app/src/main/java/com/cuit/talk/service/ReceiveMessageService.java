package com.cuit.talk.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;

import com.cuit.talk.activity.R;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Message;
import com.cuit.talk.entity.Person;
import com.cuit.talk.entity.PersonSimple;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rice on 16-10-15.
 */
public class ReceiveMessageService extends Service{
    private String myselfId;
    private MessageCallbackListener listener = null;
    //所有的消息接受列表
    private List<Message> messageList = new ArrayList<Message>();

    //回调函数接口
    public interface MessageCallbackListener {
        void onSuccess(Message message);
        void onError(String error);
    }
    //Binder类
    public class ReceiveMessageBinder extends Binder{
        public ReceiveMessageService getReceiveMessageService(){
            return ReceiveMessageService.this;
        }
        public void setMyselfId(String myselfId){
            ReceiveMessageService.this.myselfId = myselfId;
        }
    }

    /**
     * 设置回调函数
     * @param listener 回调函数引用
     */
    public void setReceiveMessageCallbackListener(MessageCallbackListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ReceiveMessageBinder();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //从服务器接受消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3000);
                        receiveMessageFromServer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 向服务器请求消息,看是否有发给自己的消息,然后接受
     */
    private void receiveMessageFromServer(){
        PersonSimple personSimple = new PersonSimple();
        personSimple.setNumber(myselfId);
        String json = new Gson().toJson(personSimple);
        OkHttpUtils.postString()
                .url("")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Message message = new Gson().fromJson(response,Message.class);
                        //调用回调函数
                        listener.onSuccess(message);
                        //把接受的消息放到接受消息列表中
                        messageList.add(message);
                        //设置通知
                        setInform(messageList);
                    }
                });

    }

    /**
     * 该方法是选择所有消息列表中的最后一个消息设置为通知
     * @param messageList 所有消息的列表
     */
    private void setInform(List<Message> messageList){
        //把最后一条消息设置为通知
        Message message = messageList.get(messageList.size()-1);
        //从数据库中查询发送者
        PersonDao personDao = PersonDao.getInsetance(getApplicationContext());
        Person sendPerson = personDao.queryPersonById(message.getSendId());
        //设置通知
        NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setContentTitle(sendPerson.getNickname())
                .setContentText(message.getContent())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.talk_touxiang);
        Notification notification = mBuilder.build();
        mManager.notify(0,notification);
    }

}

