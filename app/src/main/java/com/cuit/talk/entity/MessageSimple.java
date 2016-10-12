package com.cuit.talk.entity;

/**
 * Created by inori on 16/10/10.
 */

public class MessageSimple {
    private String messageSendPersonName;

    private String messageContent;

    private String sendTime;

    private int messageCount;

    public String getMessageSendPersonName() {
        return messageSendPersonName;
    }

    public void setMessageSendPersonName(String messageSendPersonName) {
        this.messageSendPersonName = messageSendPersonName;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }
}
