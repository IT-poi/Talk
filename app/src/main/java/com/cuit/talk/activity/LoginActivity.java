package com.cuit.talk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.entity.Person;
import com.cuit.talk.entity.PersonSimple;
import com.cuit.talk.util.HandleRemPass;
import com.cuit.talk.util.HttpCallbackListener;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;



public class LoginActivity extends Activity implements View.OnClickListener {
    private Button loginBt;
    private Button registerBt;
    private Button questionBt;
    private EditText inputNumberET;
    private EditText inputPasswordET;
    HandleRemPass handleRemPass = null;
    private String loginIpAddress = "http://196.128:8080/login/commit";//登录请求的ip地址
    private List<PersonSimple> list = null;//本地的所有用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        loginBt = (Button) findViewById(R.id.login_login_bt);
        registerBt = (Button) findViewById(R.id.login_register_bt);
        questionBt = (Button) findViewById(R.id.login_question_bt);
        inputNumberET = (EditText) findViewById(R.id.login_number_et);
        inputPasswordET = (EditText) findViewById(R.id.login_password_et);
        handleRemPass = HandleRemPass.getInstance(LoginActivity.this);
        list = handleRemPass.getAllData();
        loginBt.setOnClickListener(this);
        registerBt.setOnClickListener(this);
        questionBt.setOnClickListener(this);
        handleRememberPassword();
    }

    /**
     * 该方法是处理记住密码的。
     */
    private void handleRememberPassword() {
        inputNumberET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if(editable.toString().equals(list.get(i).getNumber())){
                            inputPasswordET.setText(list.get(i).getPassword());
                        }else {
                            inputPasswordET.setText("");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //点击登录按钮
            case R.id.login_login_bt:
                String number = inputNumberET.getText().toString();
                String password = inputPasswordET.getText().toString();
                if(TextUtils.isEmpty(number)){
                    inputNumberET.setHintTextColor(getResources().getColor(R.color.colorError));
                    inputNumberET.setHint("请输入用户帐号");
                }else if(TextUtils.isEmpty(password)){
                    inputPasswordET.setHintTextColor(getResources().getColor(R.color.colorError));
                    inputPasswordET.setHint("请输入密码");
                }else {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("personId","1");
                    startActivity(intent);
//                    String json = new Gson().toJson(personSimple);
//                    OkHttpUtils.postString()
//                            .url(loginIpAddress)
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
//                                    final PersonSimple personSimple = new PersonSimple();
//                                    personSimple.setPassword(number);
//                                    personSimple.setNumber(password);
//                                    handleRemPass.saveUserPass(personSimple);
//                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                                    startActivity(intent);
//                                }
//                            });
                }
                break;
            //点击无法登录?按钮
            case R.id.login_question_bt:

                break;
            //点击新用户注册按钮
            case R.id.login_register_bt:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
