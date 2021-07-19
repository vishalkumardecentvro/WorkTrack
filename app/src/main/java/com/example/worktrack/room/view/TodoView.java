package com.example.worktrack.room.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.worktrack.room.entitiy.TodoEntity;
import com.example.worktrack.room.repository.TodoRepository;

import java.util.List;

public class TodoView extends AndroidViewModel {

  private TodoRepository repository;

  public TodoView(@NonNull Application application) {
    super(application);
    repository = new TodoRepository(application);
  }

  public void insert(TodoEntity todoEntity) {
    repository.insertTask(todoEntity);
  }

  public void delete(TodoEntity todoEntity) {
    repository.deleteTasks(todoEntity);
  }

  public void update(TodoEntity todoEntity) {
    repository.updateTask(todoEntity);
  }

  public LiveData<List<TodoEntity>> getAllTask() {
    return repository.getTodoTasksList();
  }
}
