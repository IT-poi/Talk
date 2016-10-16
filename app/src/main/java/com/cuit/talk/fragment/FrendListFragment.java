package com.cuit.talk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cuit.talk.activity.R;
import com.cuit.talk.activity.TalkMessageActivity;
import com.cuit.talk.adapter.FriendExpandableListAdapter;
import com.cuit.talk.dao.GroupDao;
import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Group;
import com.cuit.talk.entity.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inori on 16/10/9.
 */

public class FrendListFragment extends Fragment {

    private String[] armTypes;

    private String[][] arms;

    //登陆者id
    private int personId;

    private PersonDao personDao;

    private GroupDao groupDao;

    private List<Group> groupList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData(container);
        View view = inflater.inflate(R.layout.friend_list_layout, container, false);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.friend_expandableListView_list);
        FriendExpandableListAdapter adapter = new FriendExpandableListAdapter(view.getContext(),groupList);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(v.getContext(), "点击了" + groupList.get(groupPosition).getPersonsList().get(childPosition).getNickname(), Toast.LENGTH_SHORT).show();
                int friendId = groupList.get(groupPosition).getPersonsList().get(childPosition).getId();
                Intent intent = new Intent(v.getContext(), TalkMessageActivity.class);
                intent.putExtra("personId", personId);
                intent.putExtra("friendId", friendId);
                startActivity(intent);
                return true;
            }
        });

        return view;
    }
    private void initData(ViewGroup container){
        Bundle bundle = getArguments();
        personId = bundle.getInt("personId");
        groupDao = GroupDao.getInsetance(container.getContext());
        groupList = groupDao.queryGroupListByPersonId(personId);
        Log.d("111", groupList.toString());
        if(groupList==null){
            groupList = new ArrayList<Group>();

            Group group = new Group();
            group.setId(100);
            group.setPersonId(100);
            group.setGroupName("我的好友");
            group.setCreateTime("2015-6-24");
            List<Person> personList = new ArrayList<Person>();

            Person person = new Person();
            person.setNickname("孤独的日");
            personList.add(person);
            Person person1 = new Person();
            person1.setNickname("闪烁的星");
            personList.add(person1);
            group.setPersonsList(personList);
            groupList.add(group);
        }

        armTypes = new String[]{
                "我的好友", "朋友", "家人", "那个ta", "ADC", "TANK", "法师", "刺客", "战士", "小兵"
        };
        arms = new String[][]{
                {"文档编辑", "文档排版", "文档处理", "文档打印"},
                {"表格编辑", "表格排版", "表格处理", "表格打印"},
                {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
                {"演示编辑", "演示排版", "演示处理", "演示打印"},
                {"奥巴马", "艾希", "轮子妈", "vn"},
                {"石头人", "泰坦", "波比", "哨兵"},
                {"发条", "维克托", "幸德拉", "光辉"},
                {"劫", "妖姬", "卡特琳娜", "男刀"},
                {"刀妹", "武器", "瑞文", "鳄鱼"},
                {"战士兵", "法师兵", "跑车兵", "超级兵"}
        };
    }

    private void initView(){

    }
}
