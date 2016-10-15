package com.cuit.talk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by inori on 16/10/15.
 */

public class AddFriendActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_add_layout);

    }
    public void initView(){

    }
}
