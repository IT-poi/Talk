package com.cuit.talk.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;

import com.cuit.talk.adapter.MainViewPagerAdapter;
import com.cuit.talk.entity.Message;
import com.cuit.talk.fragment.FrendListFragment;
import com.cuit.talk.fragment.MessageListFragment;
import com.cuit.talk.service.ReceiveMessageService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    public static final String MESSAGE_LIST_FILE = "message_list_file";

    private android.support.design.widget.TabLayout tableLayout;
    private ViewPager viewPager;

    private List<String> titleList = new ArrayList<String>();
    private List<Fragment> viewList = new ArrayList<Fragment>();
    private Fragment messageFragmentView, friendListFragmentView, view3, view4, view5;
    //登陆用户id
    private int personId;
    //service相关
    public static Intent receiveServiceIntent;
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
            receiveMessageBinder.setMyselfId(String.valueOf(personId));
            receiveMessageBinder.getReceiveMessageService().
                    setReceiveMessageCallbackListener(new ReceiveMessageService.MessageCallbackListener() {
                        @Override
                        public void onSuccess(Message message) {
                            //接受消息逻辑
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        personId = getIntent().getIntExtra("personId", 0);
        initData();
        initView();
//        //启动service
//        receiveServiceIntent = new Intent(MainActivity.this,ReceiveMessageService.class);
//        bindService(MainActivity.receiveServiceIntent,receiveConnection,BIND_AUTO_CREATE);
//        startService(receiveServiceIntent);

    }

    public void initData(){
        Bundle bundle = new Bundle();
        bundle.putInt("personId", personId);
        messageFragmentView = new MessageListFragment();
        messageFragmentView.setArguments(bundle);
        friendListFragmentView = new FrendListFragment();
        friendListFragmentView.setArguments(bundle);

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

    @Override
    protected void onDestroy() {
        unbindService(receiveConnection);
        stopService(receiveServiceIntent);
        super.onDestroy();
    }
}
