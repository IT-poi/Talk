package com.cuit.talk.util;

/**
 * Created by rice on 16-10-15.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
