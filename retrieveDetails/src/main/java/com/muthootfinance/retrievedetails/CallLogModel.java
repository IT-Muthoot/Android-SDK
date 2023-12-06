package com.muthootfinance.retrievedetails;

public class CallLogModel {
    private String phNumber;
    private String name;
    private String dir;
    private String callDayTime;
    private String callDuration;

    public CallLogModel(String phNumber, String name, String dir, String callDayTime, String callDuration) {
        this.phNumber = phNumber;
        this.name = name;
        this.dir = dir;
        this.callDayTime = callDayTime;
        this.callDuration = callDuration;
    }


}
