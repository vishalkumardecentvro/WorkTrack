package com.example.worktrack.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worktrack.room.entitiy.DoingEntity;

import java.util.List;

@Dao
public interface DoingDao {

  @Insert
  void insertTask(DoingEntity task);

  @Delete
  void delete(DoingEntity task);

  @Update
  void update(DoingEntity task);

  @Query("select * from doing order by priority asc")
  LiveData<List<DoingEntity>> fetchAllDoingTask();

}
