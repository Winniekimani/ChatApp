package com.winnie.chatapp.Models;

public class Message {

    String sender_id;
    String message;
    String message_id;
    long time;

    public Message() {
    }

    public Message(String sender_id, String message, String message_id, long time) {
        this.sender_id = sender_id;
        this.message = message;
        this.message_id = message_id;
        this.time = time;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
