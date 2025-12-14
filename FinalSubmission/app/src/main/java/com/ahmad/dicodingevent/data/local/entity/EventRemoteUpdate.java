package com.ahmad.dicodingevent.data.local.entity;

import androidx.room.ColumnInfo;

public class EventRemoteUpdate {
    private int id;

    private String name;

    @ColumnInfo(name = "owner_name")
    private String ownerName;

    private String description;

    @ColumnInfo(name = "begin_time")
    private String beginTime;

    @ColumnInfo(name = "end_time")
    private String endTime;

    private int quota;

    private int registrants;

    private String link;

    @ColumnInfo(name = "is_active")
    private boolean active;

    @ColumnInfo(name = "media_cover")
    private String mediaCover;

    @ColumnInfo(name = "image_logo")
    private String imageLogo;

    public EventRemoteUpdate(EventEntity event) {
        this.id = event.getId();
        this.name = event.getName();
        this.ownerName = event.getOwnerName();
        this.description = event.getDescription();
        this.beginTime = event.getBeginTime();
        this.endTime = event.getEndTime();
        this.quota = event.getQuota();
        this.registrants = event.getRegistrants();
        this.link = event.getLink();
        this.active = event.isActive();
        this.mediaCover = event.getMediaCover();
        this.imageLogo = event.getImageLogo();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getRegistrants() {
        return registrants;
    }

    public void setRegistrants(int registrants) {
        this.registrants = registrants;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMediaCover() {
        return mediaCover;
    }

    public void setMediaCover(String mediaCover) {
        this.mediaCover = mediaCover;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }
}
