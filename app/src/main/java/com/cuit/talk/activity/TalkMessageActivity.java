package com.cuit.talk.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cuit.talk.adapter.TalkMessageRecyclerViewAdapter;
import com.cuit.talk.dao.ContactDao;
import com.cuit.talk.dao.MessageDao;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Contact;
import com.cuit.talk.entity.Message;
import com.cuit.talk.entity.Person;
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
    //Views
    private Button cancel_BT;
    private Button individual_BT;
    private Button sendMessage_BT;
    private TextView myName_TV;
    private EditText inputMessage_ET;
    private RecyclerView messageList_RV;
    private ProgressBar progressBar;
    //RecyclerView相关
    private List<Message> messages = new ArrayList<Message>();
    private TalkMessageRecyclerViewAdapter adapter;
    //数据库相关
    private MessageDao messageDao;
    private ContactDao contactDao;
    private PersonDao personDao;
    private int personId;
    private int friendId;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_talk);
        setContentView(R.layout.talk_message_layout);
        loadData();
        initViews();

    }

    /**
     * 初始化views,设置监听事件
     */
    private void initViews() {
        cancel_BT = (Button) findViewById(R.id.talk_message_layout_cancel);
        cancel_BT.setOnClickListener(this);
        individual_BT = (Button) findViewById(R.id.talk_message_layout_info);
        individual_BT.setOnClickListener(this);
        sendMessage_BT = (Button) findViewById(R.id.talk_message_layout_button);
        sendMessage_BT.setOnClickListener(this);
        myName_TV = (TextView) findViewById(R.id.talk_message_layout_textView);
        myName_TV.setText(person.getNickname());
        inputMessage_ET = (EditText) findViewById(R.id.talk_message_layout_editText);
        messageList_RV = (RecyclerView) findViewById(R.id.recycler_view_talk_message_rv);
        progressBar = new ProgressBar(TalkMessageActivity.this);
        progressBar.setVisibility(View.GONE);
        //设置RecyclerView倒着加载
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        messageList_RV.setLayoutManager(linearLayoutManager);
        //设置RecycleView每个Item有固定大小,提高RecycleView性能
        messageList_RV.setHasFixedSize(true);
        //生成LayoutManager,显示ListView的效果(横向滚动的列表或者竖直滚动的列表)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TalkMessageActivity.this);
        messageList_RV.setLayoutManager(layoutManager);
        //设置自定义Adapter
        adapter = new TalkMessageRecyclerViewAdapter(messages, personId);
        adapter.setListener(new TalkMessageRecyclerViewAdapter.TalkMessageCallBackListener() {
            /**
             * 删除该条消息
             * @param context context
             * @param position 该条消息所在位置
             */
            @Override
            public void onItemLongClick(Context context, int position) {
                messageDao.deleteMessageBySendId(messages.get(position).getId());
                adapter.notifyItemRemoved(position);
                messages.remove(position);
                adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
            }
        });
        messageList_RV.setAdapter(adapter);
        messageList_RV.smoothScrollToPosition(messages.size());
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //接受MainActivity发送的数据
        personId = getIntent().getIntExtra("personId", 0);
        friendId = getIntent().getIntExtra("friendId", 0);
        messageDao = MessageDao.getInsetance(this);
        contactDao = ContactDao.getContactDao(this);
        personDao = PersonDao.getInsetance(this);
        messages = messageDao.queryMessageAll(personId, friendId);
        person = personDao.queryPersonById(friendId);
    }

    /**
     * 触发监听事件
     * @param view view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击发送按钮
            case R.id.talk_message_layout_button:
                if (!TextUtils.isEmpty(inputMessage_ET.getText())) {
                    //读取消息
                    Message message = new Message();
                    message.setSendId(personId);
                    message.setReceiveId(friendId);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(new java.util.Date(System.currentTimeMillis()));
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
                    //插入最近联系人
                    contactDao.addContact(personId, friendId);
                }
                break;
            //返回按钮
            case R.id.talk_message_layout_cancel:
                finish();
                break;
            //个人信息按钮
            case R.id.talk_message_layout_info:
                break;
            default:
                break;
        }
    }

    /**
     * 发送消息到服务器
     *
     * @param message 自己写的message
     */
    private void sendMessage(Message message) {
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
