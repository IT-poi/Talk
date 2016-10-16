package com.cuit.talk.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cuit.talk.activity.R;
import com.cuit.talk.entity.Message;

import java.util.List;

/**
 * Created by rice on 16-10-14.
 */
public class TalkMessageRecyclerViewAdapter extends RecyclerView.Adapter{
    /**
     * 根据这个messages来构建view中的item
     */
    private List<Message> messages;
    private int myselfId;
    private static final int MESSAGE_TYPE_RECEIVE = 0;
    private static final int MESSAGE_TYPE_SEND = 1;
    /**
     * 构造方法:接受数据messages
     * @param messages 所有的消息
     */
    public TalkMessageRecyclerViewAdapter(final List<Message> messages, int myselfId){

        this.messages = messages;
        this.myselfId = myselfId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getReceiveId()==myselfId){
            return MESSAGE_TYPE_RECEIVE;
        }else {
            return MESSAGE_TYPE_SEND;
        }
    }

    /**
     * 该方法创建RecyclerView.ViewHolder。
     * @param parent view的父亲
     * @param viewType
     * @return 自定义的MyHolder(view的持有者)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == MESSAGE_TYPE_RECEIVE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .talk_message_list_recieve_item,parent,false);
        }else if (viewType == MESSAGE_TYPE_SEND){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .talk_message_list_send_item,parent,false);
        }
        return new MyHolder(view);
    }

    /**
     * 该方法是把messages中的每条message与holder绑定。
     * @param holder RecyclerView.ViewHolder(view的持有者)
     * @param position messages中的一条message的位置
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Message message = messages.get(position);
        myHolder.position = position;
        if(message.getReceiveId()==myselfId){
            myHolder.messageSendTV.setText( messages.get(position).getContent());

        }else{
            myHolder.messageReceiveTV.setText(messages.get(position).getContent());

        }
    }

    /**
     *
     * @return 返回messages的大小,也就是view中item的数量
     */
    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * 自定义RecyclerView.ViewHolder类,把list中的数据绑定。
     */
    private class MyHolder extends RecyclerView.ViewHolder{
        public View rootView;
        public TextView messageSendTV;
        public TextView messageReceiveTV;
        int position;
        public MyHolder(View itemView) {
            super(itemView);
            messageReceiveTV = (TextView) itemView.findViewById(R.id.talk_message_list_recieve_item_textView);
            messageSendTV = (TextView) itemView.findViewById(R.id.talk_message_list_send_item_textView);
        }
    }
}
