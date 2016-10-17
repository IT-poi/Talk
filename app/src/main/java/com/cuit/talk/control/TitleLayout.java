package com.cuit.talk.control;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuit.talk.activity.AddFriendActivity;
import com.cuit.talk.activity.R;
import com.cuit.talk.callback.TitleHeadImageOnClickCallBack;

/**
 * Created by inori on 16/10/8.
 */

public class TitleLayout extends LinearLayout {

    //左侧头像按钮
    private RoundImageView head_imgbtn;
    //中间显示的titleName
    private TextView titleName;
    //右侧添加朋友按钮
    private Button add_friend_btn;
    //点击左侧按钮的回调接口
    private TitleHeadImageOnClickCallBack onHeadClick;

    private Context context;
    public TitleLayout(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        //初始化各控件
        head_imgbtn = (RoundImageView)findViewById(R.id.title_left_button);
        titleName = (TextView)findViewById(R.id.title_text);
        add_friend_btn = (Button)findViewById(R.id.title_right_button);
        //添加好友的监听设置
        add_friend_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击右侧添加好友时调用此回调函数,可以在调用方实现接口完成业务逻辑
                onHeadClick.onAddClickListener(context);
            }
        });
        //左侧按钮点击事件
        head_imgbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件触发时,调用此回调函数,可以在调用方实现接口完成业务逻辑
                onHeadClick.onHeadClickListener(context);
            }
        });
    }

    /**
     * 给该控件设置回调接口
     * @param onHeadClick
     */
    public void setOnHeadClickListener(TitleHeadImageOnClickCallBack onHeadClick){
        this.onHeadClick = onHeadClick;
    }

    /**
     * 给标题栏设置中间显示的内容
     * @param titleName
     */
    public void setTitleName(String titleName){
        this.titleName.setText(titleName);
    }
}
