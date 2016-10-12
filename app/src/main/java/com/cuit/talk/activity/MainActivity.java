package com.cuit.talk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cuit.talk.adapter.FriendExpandableListAdapter;
import com.cuit.talk.adapter.MainViewPagerAdapter;
import com.cuit.talk.fragment.FrendListFragment;
import com.cuit.talk.fragment.MessageListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private android.support.design.widget.TabLayout tableLayout;
    private ViewPager viewPager;


//    private String[] armTypes;
//
//    private String[][] arms;

//    private LayoutInflater inflater;
    private List<String> titleList = new ArrayList<String>();
    private List<Fragment> viewList = new ArrayList<Fragment>();
    private Fragment messageFragmentView, friendListFragmentView, view3, view4, view5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    public void initData(){
//        armTypes = new String[]{
//                "我的好友", "朋友", "家人", "那个ta", "ADC", "TANK", "法师", "刺客", "战士", "小兵"
//        };
//        arms = new String[][]{
//                {"文档编辑", "文档排版", "文档处理", "文档打印"},
//                {"表格编辑", "表格排版", "表格处理", "表格打印"},
//                {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
//                {"演示编辑", "演示排版", "演示处理", "演示打印"},
//                {"奥巴马", "艾希", "轮子妈", "vn"},
//                {"石头人", "泰坦", "波比", "哨兵"},
//                {"发条", "维克托", "幸德拉", "光辉"},
//                {"劫", "妖姬", "卡特琳娜", "男刀"},
//                {"刀妹", "武器", "瑞文", "鳄鱼"},
//                {"战士兵", "法师兵", "跑车兵", "超级兵"}
//        };

        messageFragmentView = new MessageListFragment();
        friendListFragmentView = new FrendListFragment();
        viewList.add(messageFragmentView);
        viewList.add(friendListFragmentView);
        titleList.add("消息");
        titleList.add("联系人");
    }

    public void initView(){
        viewPager = (ViewPager)findViewById(R.id.vp_view);
        tableLayout = (android.support.design.widget.TabLayout)findViewById(R.id.tabs);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);

        MainViewPagerAdapter mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), viewList, titleList);
        viewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        tableLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
    }
}
