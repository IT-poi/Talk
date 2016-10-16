package com.cuit.talk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
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
import com.cuit.talk.dao.MessageDao;
import com.cuit.talk.entity.Message;
import com.cuit.talk.service.ReceiveMessageService;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private MessageDao messageDao;
    private int personId;
    private int friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_talk);
        setContentView(R.layout.talk_message_layout);
        initData();
        initViews();

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
        adapter = new TalkMessageRecyclerViewAdapter(messages,personId);
        messageList_RV.setAdapter(adapter);
        messageList_RV.smoothScrollToPosition(messages.size());

    }
    private void initData() {
        //接受MainActivity发送的数据
        personId =  getIntent().getIntExtra("personId",0);
        friendId = getIntent().getIntExtra("friendId",0);
        messageDao = MessageDao.getInsetance(this);
        messages = messageDao.queryMessageAll(personId,friendId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.talk_message_layout_button:
                if(!TextUtils.isEmpty(inputMessage_ET.getText())){
                    //读取消息
                    Message message = new Message();
                    message.setSendId(personId);
                    message.setReceiveId(friendId);
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    String date=sdf.format(new java.util.Date(System.currentTimeMillis()));
                    message.setSendTime(date);
                    message.setContent(inputMessage_ET.getText().toString());
                    //更新消息列表
                    inputMessage_ET.setText("");
                    messages.add(message);
                    adapter.notifyDataSetChanged();
                    messageList_RV.smoothScrollToPosition(messages.size());
                    //发送消息到服务器
//                    sendMessage(message);
                    //插入消息到本地数据库
                    messageDao.insertMessage(message);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 发送消息到服务器
     * @param message 自己写的message
     */
    private void sendMessage(Message message){
        String json = new Gson().toJson(message);
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

                    }
                });
    }
}
