package com.cuit.talk.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.cuit.talk.util.HandleRemPass;

public class LoginActivity extends Activity {
    private Button loginBt;
    private Button registerBt;
    private Button questionBt;
    private EditText inputNumberET;
    private EditText inputPasswordET;
    private HandleRemPass handleRemPass;
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
        handleRememberPassword();
    }

    private void handleRememberPassword(){
        handleRemPass = HandleRemPass.getInstance(LoginActivity.this);
        final String[] allData = handleRemPass.getAllData().split(";");
        Log.d("afterTextChanged1", handleRemPass.getAllData());
        inputNumberET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("beforeTextChanged", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("onTextChanged", (String) charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("afterTextChanged2", editable.toString());
                Log.d("afterTextChanged4", String.valueOf(allData.length));
                for(int i = 0;i<allData.length;i++){
                    if(editable.toString().equals(allData[i].split(",")[0])){
                        inputPasswordET.setText(allData[i].split(",")[1]);
                    }else {
                        inputNumberET.setText("");
                    }
                }
            }
        });
    }
}
