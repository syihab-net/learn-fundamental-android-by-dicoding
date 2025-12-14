package com.ahmad.dicodingevent.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ahmad.dicodingevent.data.local.entity.EventEntity;

@Database(entities = {EventEntity.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract EventDao eventDao();

    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(
                                context.getApplicationContext(),
                                AppRoomDatabase.class,
                                "event_database"
                        )
                        .build();
            }
        }

        return INSTANCE;
    }
}
