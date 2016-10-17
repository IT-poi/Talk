package com.cuit.talk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.dao.PersonDao;
import com.cuit.talk.entity.Person;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by inori on 16/10/14.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private EditText number_et;
    private EditText nickname_et;

    private EditText password_et;

    private EditText pass_sure_et;

    private Button commit_btn;

    private Button title_back;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.talk_register_layout);
        initView();

    }
    private void initView(){
        nickname_et = (EditText) findViewById(R.id.talk_register_nickname_ev);
        password_et = (EditText) findViewById(R.id.talk_register_password_ev);
        pass_sure_et = (EditText) findViewById(R.id.talk_register_pass_sure_ev);
        number_et = (EditText) findViewById(R.id.talk_register_number_ev);
        commit_btn = (Button) findViewById(R.id.talk_register_commit_btn);
        title_back = (Button) findViewById(R.id.talk_register_title_return_btn);
        title_back.setOnClickListener(this);
        commit_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.talk_register_commit_btn:
                //--提交逻辑
                personDao = PersonDao.getInsetance(this);
                String number = number_et.getText().toString();
                String nickmame = nickname_et.getText().toString();
                String password = password_et.getText().toString();
                String passSure = pass_sure_et.getText().toString();
                Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                if(nickmame.isEmpty()) {
                    nickname_et.setText("");
                    nickname_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    nickname_et.setHint("请输入昵称");
                }else if(number.isEmpty()) {
                    number_et.setText("");
                    number_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    number_et.setHint("请输入手机号");
                } else if(!p.matcher(number).matches()) {
                    number_et.setText("");
                    number_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    number_et.setHint("非法手机号码");
                } else if (password.isEmpty()){
                    password_et.setText("");
                    password_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    password_et.setHint("请输入密码");
                }else if (passSure.isEmpty()){
                    pass_sure_et.setText("");
                    pass_sure_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    pass_sure_et.setHint("请输入确认密码");
                }else if (password.equals(passSure)){
                    String url = "http://196.128:8080/register/commit";
                    Person person = new Person();
                    person.setNickname(nickmame);
                    person.setPassword(password);

//                    String json = new Gson().toJson(person);
//                    OkHttpUtils.postString()
//                            .url(url)
//                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                            .content(json)
//                            .build()
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onError(Request request, Exception e) {
//
//                                }
//
//                                @Override
//                                public void onResponse(String response) {
//                                    finish();
//                                }
//                            });
                }else{
                    pass_sure_et.setText("");
                    pass_sure_et.setHintTextColor(getResources().getColor(R.color.colorError));
                    pass_sure_et.setHint("两次密码不一致");
                }
                break;
            case R.id.talk_register_title_return_btn:
                finish();
                break;
            default:
                break;
        }

    }
}
