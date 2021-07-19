package com.example.worktrack.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worktrack.room.entitiy.TodoEntity;

import java.util.List;

@Dao
public interface TodoDao {
  @Insert
  void insertTask(TodoEntity task);

  @Delete
  void delete(TodoEntity task);

  @Update
  void update(TodoEntity task);

  @Query("select * from todo order by priority asc")
  LiveData<List<TodoEntity>> fetchAllTodoTask();
}
