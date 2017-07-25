package com.research.andrade.andar;

/**
 * Created by Onyok on 3/2/2017.
 */

public class MessageResults {

    private String receiver = "";
    private String body = "";
    private String day = "";
    private String time = "";

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
