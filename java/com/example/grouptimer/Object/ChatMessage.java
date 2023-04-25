package com.example.grouptimer.Object;

import java.util.Map;

public class ChatMessage
{
    public String Message;
    public String SenderUID;

    public long SendTime;

    public Map<String, Boolean> MemberIndice;


    public ChatMessage()
    {

    }


    public ChatMessage(String message, String senderUID, long sendTime, Map<String, Boolean> memberIndice)
    {
        this.Message = message;
        this.SenderUID = senderUID;
        this.SendTime = sendTime;
        this.MemberIndice = memberIndice;
    }
}
