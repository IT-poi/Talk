package com.cuit.talk.callback;

import android.content.Context;

/**
 * 该接口为qq上方title左侧按钮的回调,主要实现按钮点击事件的回调
 * Created by inori on 16/10/17.
 */

public interface TitleHeadImageOnClickCallBack {
    /**
     * 通过在调用方实现该方法可以处理点击左侧头像的业务逻辑
     * @param context
     */
    public void onHeadClickListener(Context context);

    /**
     * 通过在调用方实现该方法可以处理点击右侧添加好友按钮的业务逻辑
     * @param context
     */
    public void onAddClickListener(Context context);
}
