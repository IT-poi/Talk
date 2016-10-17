package com.cuit.talk.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.dao.GroupDao;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Friend;
import com.cuit.talk.entity.Group;
import com.cuit.talk.entity.Person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by inori on 16/10/15.
 */

public class AddFriendActivity extends Activity {

    private EditText friendNumber_et;

    private Button title_return_btn;

    private Button search_friend_btn;

    private PersonDao personDao;

    private GroupDao groupDao;

    private int personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_add_layout);
        initData();
        initView();

    }

    /**
     * 初始化视图
     */
    public void initView(){

        friendNumber_et = (EditText)findViewById(R.id.add_friend_query_person_ev);
        title_return_btn = (Button)findViewById(R.id.add_friend_title_return_btn);
        search_friend_btn = (Button)findViewById(R.id.add_friend_query_person_btn);

        title_return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personDao = PersonDao.getInsetance(AddFriendActivity.this);
                String friendNumber = friendNumber_et.getText().toString();
                Person person = null;
                if(friendNumber.isEmpty()){
                    friendNumber_et.setText("");
                    friendNumber_et.setHint("请输入用户账号");
                }else{
                    person = personDao.queryPersonByNumber(friendNumber);
                    if (person == null){
                        friendNumber_et.setText("");
                        friendNumber_et.setHint("没有该用户");
                    }else{
                        groupDao =GroupDao.getInsetance(AddFriendActivity.this);
                        List<Group> groups = groupDao.queryGroupListByPersonId(personId);
                        if (groups==null || groups.size() <= 0){
                            Date date=new Date();
                            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Group group = new Group();
                            group.setPersonId(personId);
                            group.setCreateTime(df.toString());
                            group.setGroupName("我的好友");
                            groupDao.addGroup(group);
                        }else{
                            Date date=new Date();
                            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            int groupId = groups.get(0).getId();
                            Friend friend = new Friend();
                            friend.setGroupId(groupId);
                            friend.setPersonId(personId);
                            friend.setFriendId(person.getId());
                            friend.setCreateTime(df.toString());
                            boolean flag = personDao.addFriend(friend);
                            if(flag){
                                friendNumber_et.setText("");
                                String str="找到用户:"+person.getNickname()+",已添加至我的好友";
                                int fstart=str.indexOf(person.getNickname());
                                int fend=fstart+person.getNickname().length();
                                SpannableStringBuilder style=new SpannableStringBuilder(str);
                                style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTitle)), fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                friendNumber_et.setText(style);
                                friendNumber_et.setSelection(style.length());
                            }else{
                                friendNumber_et.setText("");
                                friendNumber_et.setHint("该用户已经在你的好友列表中了");
                            }
                        }

                    }
                }
            }
        });
    }

    private void initData(){
        personId = getIntent().getIntExtra("personId", 0);
    }
}
