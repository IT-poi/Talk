package com.cuit.talk.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cuit.talk.activity.R;
import com.cuit.talk.entity.MessageSimple;
import com.cuit.talk.entity.Person;

import java.util.List;

/**
 * Created by inori on 16/10/6.
 */
public class MessageListRecyviewAdapter extends RecyclerView.Adapter {

    public static interface OnRecyclerViewListener{
        /**
         *  点击item会回调此函数,此函数的业务逻辑在调用地实现(回调机制)
         * @param position
         */
        void onItemClick(Context context, int position);

        /**
         * 长按item会回调此函数,同上
         * @param position
         * @return
         */
        boolean onItemLongClick(Context context, int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public static final String TAG = MessageListRecyviewAdapter.class.getSimpleName();  //日志

    /**
     * 根据这个list来构建view的item
     */
    private List<MessageSimple> list;

    public MessageListRecyviewAdapter(List<MessageSimple> list) {
        this.list = list;
    }







    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder, i:" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(lp);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position + " , holder: " + holder);
        MessageViewHolder viewHolder = (MessageViewHolder)holder;
        MessageSimple messageSimple = list.get(position);
        viewHolder.position = position;
//        viewHolder.nameTv.setText(messageSimple.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public View rootView;
        public ImageView touxiangIv;
        public TextView nameTv;
        public TextView messageTv;
        public TextView sendTimeTv;
        public TextView messageCountTv;
        public int position;

        public MessageViewHolder(View itemView) {
            super(itemView);
            touxiangIv = (ImageView) itemView.findViewById(R.id.message_list_item_touxiang_Iv);
            nameTv = (TextView)itemView.findViewById(R.id.message_list_item_name);
            messageTv = (TextView)itemView.findViewById(R.id.message_list_item_lastmessage);
            sendTimeTv = (TextView)itemView.findViewById(R.id.message_list_item_time);
            messageCountTv = (TextView)itemView.findViewById(R.id.message_list_item_messagecount);
            rootView = itemView.findViewById(R.id.item_message_list_view);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(null != onRecyclerViewListener){
                onRecyclerViewListener.onItemClick(view.getContext(), position);
            }

        }

        @Override
        public boolean onLongClick(View view) {
            if(null != onRecyclerViewListener){
                onRecyclerViewListener.onItemLongClick(view.getContext(), position);
            }
            return false;
        }
    }
}
