package com.ahmad.dicodingevent.data.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ListEventResponse {

    @SerializedName("listEvents")
    private List<Event> listEvents;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<Event> getListEvents() {
        return listEvents;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}