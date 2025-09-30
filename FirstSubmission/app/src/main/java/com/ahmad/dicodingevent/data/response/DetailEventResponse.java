package com.ahmad.dicodingevent.data.response;

import com.google.gson.annotations.SerializedName;

public class DetailEventResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("event")
    private Event event;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Event getEvent() {
        return event;
    }
}