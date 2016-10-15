package com.cuit.talk.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.adapter.TalkMessageRecyviewAdapter;
import com.cuit.talk.entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestTalkActivity extends Activity {
    private Button sendMessage_BT;
    private EditText inputMessage_ET;
    private RecyclerView messageList_RV;
    private List<Message> messages = new ArrayList<Message>();
    private TalkMessageRecyviewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_talk);
        setContentView(R.layout.talk_message_layout);
        sendMessage_BT= (Button) findViewById(R.id.talk_message_layout_button);
        inputMessage_ET= (EditText) findViewById(R.id.talk_message_layout_editText);

        messageList_RV = (RecyclerView) findViewById(R.id.recycler_view_talk_message_rv);
        messageList_RV.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TestTalkActivity.this);
        messageList_RV.setLayoutManager(layoutManager);

        initData();
        adapter = new TalkMessageRecyviewAdapter(messages,1);
        messageList_RV.setAdapter(adapter);
    }

    private void initData() {
        Message message = new Message();
        message.setContent("你好");
        message.setId(1);
        message.setSendId(0);
        message.setReceiveId(1);
        Message message1 = new Message();
        message1.setContent("哈哈");
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


}
