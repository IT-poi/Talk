package com.cuit.talk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cuit.talk.adapter.TalkMessageRecyclerViewAdapter;
import com.cuit.talk.entity.Message;
import com.cuit.talk.service.ReceiveMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TalkMessageActivity extends Activity implements View.OnClickListener {
    private static final int SEND = 0;
    private static final int RECEIVE = 1;
    //Views
    private Button sendMessage_BT;
    private EditText inputMessage_ET;
    private RecyclerView messageList_RV;
    private ProgressBar progressBar;
    //RecyclerView相关
    private List<Message> messages = new ArrayList<Message>();
    private TalkMessageRecyclerViewAdapter adapter;
    //接受消息service
    private ReceiveMessageService.ReceiveMessageBinder receiveMessageBinder;
    private ServiceConnection receiveConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            receiveMessageBinder = (ReceiveMessageService.ReceiveMessageBinder) iBinder;
            //回调函数处理接受消息。
            receiveMessageBinder.getReceiveMessageService().
                    setReceiveMessageCallbackListener(new ReceiveMessageService.MessageCallbackListener() {
                @Override
                public void onSuccess(Message message) {
                    messages.add(message);
                    adapter.notifyDataSetChanged();
                    messageList_RV.smoothScrollToPosition(messages.size());
                }
                @Override
                public void onError(String error) {

                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_talk);
        setContentView(R.layout.talk_message_layout);
        initData();
        initViews();
        //bindService
        bindService(MainActivity.receiveServiceIntent,receiveConnection,BIND_AUTO_CREATE);
    }
    private void initViews(){
        sendMessage_BT= (Button) findViewById(R.id.talk_message_layout_button);
        sendMessage_BT.setOnClickListener(this);
        inputMessage_ET= (EditText) findViewById(R.id.talk_message_layout_editText);
        messageList_RV = (RecyclerView) findViewById(R.id.recycler_view_talk_message_rv);
        progressBar = new ProgressBar(TalkMessageActivity.this);
        progressBar.setVisibility(View.GONE);
        //设置RecyclerView倒着加载
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        messageList_RV.setLayoutManager(linearLayoutManager);
        //
        messageList_RV.setHasFixedSize(true);
        //
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TalkMessageActivity.this);
        messageList_RV.setLayoutManager(layoutManager);
        //设置Adapter
        adapter = new TalkMessageRecyclerViewAdapter(messages,1);
        messageList_RV.setAdapter(adapter);

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
                if(!TextUtils.isEmpty(inputMessage_ET.getText())){
                    Message message = new Message();
                    message.setContent(inputMessage_ET.getText().toString());
                    sendMessage(message);
                }
                break;
            default:
                break;
        }
    }
    private void sendMessage(Message message){
        inputMessage_ET.setText("");
        messages.add(message);
        adapter.notifyDataSetChanged();
        messageList_RV.smoothScrollToPosition(messages.size());
//        String json = new Gson().toJson(message);
//        OkHttpUtils.postString()
//                .url("")
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content(json)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                });
    }
    @Override
    protected void onDestroy() {
        unbindService(receiveConnection);//解绑服务
        super.onDestroy();
    }
}
