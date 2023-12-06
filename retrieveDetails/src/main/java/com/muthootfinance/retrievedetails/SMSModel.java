package com.muthootfinance.retrievedetails;

public class SMSModel {
    private String from;
    private String body;
    private String readState;
    private String time;

    public SMSModel(String from, String body, String readState, String time) {
        this.from = from;
        this.body = body;
        this.readState = readState;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
