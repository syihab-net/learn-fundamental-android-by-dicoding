package com.ahmad.dicodingevent.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ahmad.dicodingevent.data.remote.response.EventItem;

@Entity
public class EventEntity implements Parcelable {
    @PrimaryKey
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

    @ColumnInfo(name = "is_active", defaultValue = "0")
    private boolean active;

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    private boolean favorite;

    @ColumnInfo(name = "media_cover")
    private String mediaCover;

    @ColumnInfo(name = "image_logo")
    private String imageLogo;

    public EventEntity() {
    }

    public EventEntity(EventItem event) {
        this.id = event.getId();
        this.name = event.getName();
        this.ownerName = event.getOwnerName();
        this.description = event.getDescription();
        this.beginTime = event.getBeginTime();
        this.endTime = event.getEndTime();
        this.quota = event.getQuota();
        this.registrants = event.getRegistrants();
        this.link = event.getLink();
        this.mediaCover = event.getMediaCover();
        this.imageLogo = event.getImageLogo();
    }

    protected EventEntity(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ownerName = in.readString();
        description = in.readString();
        beginTime = in.readString();
        endTime = in.readString();
        quota = in.readInt();
        registrants = in.readInt();
        link = in.readString();
        active = in.readByte() != 0;
        favorite = in.readByte() != 0;
        mediaCover = in.readString();
        imageLogo = in.readString();
    }

    public static final Creator<EventEntity> CREATOR = new Creator<>() {
        @Override
        public EventEntity createFromParcel(Parcel in) {
            return new EventEntity(in);
        }

        @Override
        public EventEntity[] newArray(int size) {
            return new EventEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(ownerName);
        parcel.writeString(description);
        parcel.writeString(beginTime);
        parcel.writeString(endTime);
        parcel.writeInt(quota);
        parcel.writeInt(registrants);
        parcel.writeString(link);
        parcel.writeByte((byte) (active ? 1 : 0));
        parcel.writeByte((byte) (favorite ? 1 : 0));
        parcel.writeString(mediaCover);
        parcel.writeString(imageLogo);
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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
