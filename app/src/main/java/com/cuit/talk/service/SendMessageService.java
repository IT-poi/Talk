package com.cuit.talk.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cuit.talk.entity.Message;

/**
 * Created by rice on 16-10-15.
 */
public class SendMessageService extends Service{
    private static final String TAG = "SendMessageService";
    Message message = null;

    public class MyBinder extends Binder{
        public SendMessageService getSendMessageService(){
            return SendMessageService.this;
        }
        public void startSendMessage(Message message){
            Log.d(TAG,"MyBinder.startSendMessage()");
            SendMessageService.this.message = message;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind()");
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(dataCallback!=null){
                        Log.d(TAG,"onStartCommand.hello");
                        Log.d("onStartCommand.hello",message.getContent());
                        dataCallback.dataChanged(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG,"onDestroy()");
        super.onDestroy();
    }
    //回调函数
    public interface DataCallback{
        void dataChanged(Message message);
    }
    DataCallback dataCallback = null;
    public DataCallback getDataCallback(){
        return dataCallback;
    }
    public void setDataCallback(DataCallback dataCallback){
        this.dataCallback = dataCallback;
    }


}
