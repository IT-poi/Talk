package com.cuit.talk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.cuit.talk.activity.R;
import com.cuit.talk.adapter.FriendExpandableListAdapter;

/**
 * Created by inori on 16/10/9.
 */

public class FrendListFragment extends Fragment {

    private String[] armTypes;

    private String[][] arms;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.friend_list_layout, container, false);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.friend_expandableListView_list);
        FriendExpandableListAdapter adapter = new FriendExpandableListAdapter(view.getContext(),armTypes,arms);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(v.getContext(), "点击了" + arms[groupPosition][childPosition].toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }

    private void initData(){
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
