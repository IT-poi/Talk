package com.cuit.talk.entity;

import java.io.Serializable;

/**
 * Created by inori on 16/10/9.
 */

public class Message implements Serializable {
    //id
    private int id;
    //发送消息者id
    private int sendId;
    //接收消息者id
    private int receiveId;
    //消息的内容
    private String content;
    //消息的发送时间
    private String sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
