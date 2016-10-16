package com.cuit.talk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.adapter.TalkMessageRecyviewAdapter;
import com.cuit.talk.entity.Message;
import com.cuit.talk.service.SendMessageService;
import com.zhy.http.okhttp.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TestTalkActivity extends Activity implements View.OnClickListener {
    private Button sendMessage_BT;
    private EditText inputMessage_ET;
    private RecyclerView messageList_RV;
    private List<Message> messages = new ArrayList<Message>();
    private TalkMessageRecyviewAdapter adapter;
    //异步消息机制
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            Message me = (Message) msg.getData().getSerializable("str");
            Log.d("handleMessage",me.getContent());
            messages.add(me);
            adapter.notifyDataSetChanged();
        }
    };
    //service相关
    private SendMessageService.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("Activity","onServiceDisconnected()");

        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("Activity","onServiceConnected()");
            myBinder = (SendMessageService.MyBinder) iBinder;
            myBinder.getSendMessageService().setDataCallback(new SendMessageService.DataCallback() {
                @Override
                public void dataChanged(Message me) {
                    Log.d("onServiceConnected",me.getContent());
                    android.os.Message message = new android.os.Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("str",me);
                    message.setData(bundle);
                    //发送消息
                    handler.sendMessage(message);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_talk);
        setContentView(R.layout.talk_message_layout);
        sendMessage_BT= (Button) findViewById(R.id.talk_message_layout_button);
        sendMessage_BT.setOnClickListener(this);
        inputMessage_ET= (EditText) findViewById(R.id.talk_message_layout_editText);

        messageList_RV = (RecyclerView) findViewById(R.id.recycler_view_talk_message_rv);
        messageList_RV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TestTalkActivity.this);
        messageList_RV.setLayoutManager(layoutManager);

        initData();
        adapter = new TalkMessageRecyviewAdapter(messages,1);
        messageList_RV.setAdapter(adapter);
        //service相关
        Log.d("Activity","onCreate");
//        Intent intent = new Intent(this,SendMessageService.class);
//        startService(intent);
//        Intent bindIntent = new Intent(this,SendMessageService.class);
//        bindService(bindIntent,connection,BIND_AUTO_CREATE);
    }

    private void initData() {
        Message message = new Message();
        message.setId(1);
        message.setSendId(0);
        message.setReceiveId(1);
        Message message1 = new Message();
        message1.setSendId(1);
        message1.setReceiveId(0);
        Random random = new Random(47);
        for(int i = 0;i<50;i++){
            switch (random.nextInt(2)){
                case 1:
                    messages.add(message);
                    break;
                case 0:
                    messages.add(message1);
                    break;
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.talk_message_layout_button:
                Message message = new Message();
                message.setContent(inputMessage_ET.getText().toString());
                Intent bindIntent = new Intent(this,SendMessageService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                myBinder.startSendMessage(new Message(){});
                Intent intent = new Intent(this,SendMessageService.class);
                startService(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("Activity","onDestroy()");
        unbindService(connection);//解绑服务
        super.onDestroy();
    }
}
