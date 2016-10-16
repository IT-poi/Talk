package com.cuit.talk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Person;

/**
 * Created by inori on 16/10/15.
 */

public class AddFriendActivity extends Activity {

    private EditText friendNumber_et;

    private Button title_return_btn;

    private Button search_friend_btn;

    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_add_layout);
        initView();

    }
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
                Person person = personDao.queryPersonByNumber(friendNumber_et.getText().toString());
                if (person == null){
                    friendNumber_et.setHint("没有该用户");
                }else{
                    friendNumber_et.setHint("找到该用户");
                }
            }
        });
    }
}
