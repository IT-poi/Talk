package com.cuit.talk.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuit.talk.activity.MainActivity;
import com.cuit.talk.activity.R;
import com.cuit.talk.activity.TalkMessageActivity;
import com.cuit.talk.adapter.MessageListRecycleViewAdapter;
import com.cuit.talk.dao.ContactDao;
import com.cuit.talk.dao.MessageDao;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.dependen.DividerItemDecoration;
import com.cuit.talk.entity.Contact;
import com.cuit.talk.entity.Message;
import com.cuit.talk.entity.MessageSimple;
import com.cuit.talk.entity.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inori on 16/10/9.
 */

public class MessageListFragment extends Fragment {
    private MessageListRecycleViewAdapter adapter;
    private List<MessageSimple> messageSimpleList = new ArrayList<MessageSimple>();
    //登陆者id
    private int personId;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //取得的自己的id
        Bundle bundle = getArguments();
        personId = bundle.getInt("personId");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_list_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_message_list_rv);
        //使recyclerView保持固定大小 这样会提高RecyclerView的性能
        recyclerView.setHasFixedSize(true);
        //生成LayoutManager,显示ListView的效果(横向滚动的列表或者竖直滚动的列表)
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        //RecyclerView加载自定义Adapter
        adapter = new MessageListRecycleViewAdapter(messageSimpleList);
        //设置Adapter回调函数
        adapter.setOnRecyclerListener(new MessageListRecycleViewAdapter.OnRecyclerViewListener() {
            /**
             * 该方法是点击该联系人Item后进入聊天界面
             * @param context context
             * @param position 点击Item的位置
             */
            @Override
            public void onItemClick(Context context, int position) {
                Intent intent = new Intent(context, TalkMessageActivity.class);
                intent.putExtra("personId",personId);
                intent.putExtra(" ",messageSimpleList.get(position).getFriendId());
                startActivity(intent);
            }
            /**
             * 长按删除该Item
             * @param context context
             * @param position 点击Item的位置
             * @return false
             */
            @Override
            public boolean onItemLongClick(Context context, int position) {
                MessageSimple messageSimple = messageSimpleList.get(position);
                adapter.notifyItemRemoved(position);
                messageSimpleList.remove(position);
                deleteMessageSimple(context,personId,messageSimple.getFriendId());
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        //设置Item之间分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                view.getContext(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    @Override
    public void onStart() {
        loadContact(getActivity());
        super.onStart();
    }

    /**
     * 加载联系人数据
     */
    private void loadContact(Context context){
        queryMessageSimpleList(context,personId);
        adapter.notifyDataSetChanged();
    }

    /**
     * 该方法是从数据库中加载MessageSimpleList
     * @param context context
     * @param personId 自己的id
     */
    private void queryMessageSimpleList(Context context,int personId) {
        messageSimpleList.clear();
        List<Contact> contactList = new ArrayList<Contact>();
        MessageSimple messageSimple = null;
        Person person = null;
        Message message = null;
        List<Message> messageList = null;
        ContactDao contactDao = ContactDao.getContactDao(context);
        PersonDao personDao = PersonDao.getInsetance(context);
        MessageDao messageDao = MessageDao.getInsetance(context);
        contactList = contactDao.queryAllContactByPersonId(personId);
        for (Contact contact : contactList) {
            messageSimple = new MessageSimple();
            person = personDao.queryPersonById(contact.getFriendId());
            messageList = messageDao.queryMessageAll(contact.getPersonId(), contact.getFriendId());
            message = messageList.get(messageList.size() - 1);
            messageSimple.setFriendId(contact.getFriendId());
            messageSimple.setFriendNickname(person.getNickname());
            messageSimple.setLastMessageTime(message.getSendTime());
            messageSimple.setMessageContent(message.getContent());
            messageSimple.setMessageCount(0);
            messageSimpleList.add(messageSimple);
        }
    }

    /**
     * 该方法删除数据库中的MessageSimple
     * @param context context
     * @param personId 自己的id
     * @param friendId 好友的id
     */
    private void deleteMessageSimple(Context context,int personId,int friendId){
        ContactDao contactDao = ContactDao.getContactDao(context);
        contactDao.deleteContact(personId,friendId);
    }


}
