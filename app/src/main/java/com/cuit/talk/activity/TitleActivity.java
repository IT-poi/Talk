package com.cuit.talk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inori on 16/10/8.
 */

public class TitleActivity extends Activity {

    private Button add_friend_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.title_layout);
        add_friend_btn = (Button)findViewById(R.id.title_right_button);
        add_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });

    }
}
