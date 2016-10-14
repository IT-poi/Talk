package com.cuit.talk.control;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cuit.talk.activity.R;

/**
 * Created by rice on 16-10-14.
 */
public class TalkTitleLayout extends LinearLayout {
    public TalkTitleLayout(Context context,AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.talk_title_layout,this);
    }
}
