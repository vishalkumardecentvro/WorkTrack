package com.myapp.worktrack;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myapp.worktrack.room.dao.DoingDao;
import com.myapp.worktrack.room.dao.TodoDao;
import com.myapp.worktrack.room.entitiy.DoingEntity;
import com.myapp.worktrack.room.entitiy.TodoEntity;

@androidx.room.Database(entities = {DoingEntity.class, TodoEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

  public static Database databaseInstance;

  public static synchronized Database getDatabaseInstance(Context context) {
    if (databaseInstance == null) {

      databaseInstance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "task")
              .fallbackToDestructiveMigrationFrom()
              .build();
    }

    return databaseInstance;
  }

  public abstract DoingDao doingDao();
  public abstract TodoDao todoDao();
}
