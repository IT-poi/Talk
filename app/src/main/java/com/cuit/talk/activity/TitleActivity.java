package com.cuit.talk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by inori on 16/10/8.
 */

public class TitleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.title_layout);
    }
}
