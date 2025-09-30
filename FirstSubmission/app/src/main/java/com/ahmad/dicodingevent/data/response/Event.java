package com.ahmad.dicodingevent.data.response;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("summary")
    private String summary;

    @SerializedName("mediaCover")
    private String mediaCover;

    @SerializedName("registrants")
    private int registrants;

    @SerializedName("imageLogo")
    private String imageLogo;

    @SerializedName("link")
    private String link;

    @SerializedName("description")
    private String description;

    @SerializedName("ownerName")
    private String ownerName;

    @SerializedName("cityName")
    private String cityName;

    @SerializedName("quota")
    private int quota;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("beginTime")
    private String beginTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("category")
    private String category;

    public String getSummary() {
        return summary;
    }

    public String getMediaCover() {
        return mediaCover;
    }

    public int getRegistrants() {
        return registrants;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCityName() {
        return cityName;
    }

    public int getQuota() {
        return quota;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCategory() {
        return category;
    }
}