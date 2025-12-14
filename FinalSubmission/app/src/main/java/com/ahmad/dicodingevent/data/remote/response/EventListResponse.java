package com.ahmad.dicodingevent.data.remote.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class EventListResponse {

    @SerializedName("listEvents")
    private List<EventItem> listEventItems;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public List<EventItem> getListEvents() {
        return listEventItems;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}