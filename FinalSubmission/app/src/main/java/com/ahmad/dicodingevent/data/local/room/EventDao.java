package com.ahmad.dicodingevent.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ahmad.dicodingevent.data.local.entity.EventEntity;
import com.ahmad.dicodingevent.data.local.entity.EventRemoteUpdate;

import java.util.List;

@Dao
public interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EventEntity event);

    @Update(entity = EventEntity.class)
    void update(EventRemoteUpdate event);

    @Update
    void update(EventEntity event);

    @Query("SELECT * FROM EventEntity WHERE is_active = :active ORDER BY begin_time DESC")
    LiveData<List<EventEntity>> getAllEvents(boolean active);

    @Query("SELECT * FROM EventEntity WHERE is_favorite = 1 ORDER BY begin_time DESC")
    LiveData<List<EventEntity>> getFavoriteEvents();

    @Query("SELECT * FROM EventEntity WHERE ID = :id LIMIT 1")
    LiveData<EventEntity> getEventById(int id);

    @Query("SELECT COUNT(ID) FROM EventEntity WHERE ID = :id")
    boolean exists(int id);
}


