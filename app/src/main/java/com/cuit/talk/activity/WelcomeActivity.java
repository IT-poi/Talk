package com.cuit.talk.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cuit.talk.dao.GroupDao;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Friend;
import com.cuit.talk.entity.Group;
import com.cuit.talk.entity.Person;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by inori on 16/10/16.
 */

public class WelcomeActivity extends Activity {

    public static final String WELCOME_FILE = "welcome_file";

    private Handler handler;

    SharedPreferences.Editor editor;

    SharedPreferences pref;

    private PersonDao personDao;

    private GroupDao groupDao;

    private String[] firstName = new String[]{
            "友好的", "聪明的", "顽强的", "呆萌的", "酷酷的", "美美的", "忧伤的", "变态的", "笨拙的", "傻傻的"
    };
    private String[] lastName = new String[]{
            "少女", "谎言", "少年", "萝莉", "真实", "噩梦", "现实", "僵尸", "西瓜", "幼女"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_welcome_layout);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        Intent intent1 = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1);
                    initData();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initData(){
        pref = getSharedPreferences(WELCOME_FILE, MODE_PRIVATE);
        editor = getSharedPreferences(WELCOME_FILE, MODE_PRIVATE).edit();
        if (pref.getBoolean("firstUse", true)){
            personDao = PersonDao.getInsetance(this);
            groupDao = GroupDao.getInsetance(this);
            String name = "夕阳下的枫叶林";
            String sex;
            //随机生成30个用户
            for (int i = 0; i<30; i++){
                if (i%2 == 0){
                    sex = "男";
                }else{
                    sex = "女";
                }
                java.util.Random random=new java.util.Random();// 定义随机类
                int result1 = random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10

                int result2=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
                name = firstName[result1] + lastName[result2];
                personDao.insertPerson(createPerson(i, name, sex));
            }
            groupDao.addGroup(createGroup(0, "我的好友"));
            groupDao.addGroup(createGroup(1,"同学"));
            groupDao.addGroup(createGroup(2, "朋友"));
            groupDao.addGroup(createGroup(3, "我的好友"));
            Friend friend = new Friend();
            friend.setId(0);
            friend.setGroupId(0);
            friend.setPersonId(10);
            friend.setFriendId(1);
            friend.setCreateTime("2015-08-10");
            personDao.addFriend(friend);
            Friend friend1 = new Friend();
            friend1.setId(1);
            friend1.setGroupId(0);
            friend1.setPersonId(10);
            friend1.setFriendId(2);
            friend1.setCreateTime("2015-08-10");
            personDao.addFriend(friend1);
            Friend friend2 = new Friend();
            friend2.setId(2);
            friend2.setGroupId(3);
            friend2.setPersonId(10);
            friend2.setFriendId(3);
            friend2.setCreateTime("2015-08-10");
            personDao.addFriend(friend2);
            editor.putBoolean("firstUse", false);
            editor.apply();
        }
    }

    public Person createPerson(int id, String name, String sex){
        Person person = new Person(id, "10011101"+id, "123456", name, "没有name", sex, 18, "11925843637", "中国");
        return person;
    }

    public Group createGroup(int id, String groupName){
        Group group = new Group(id, groupName, 10, "2015-08-11", null);
        return group;
    }
}
