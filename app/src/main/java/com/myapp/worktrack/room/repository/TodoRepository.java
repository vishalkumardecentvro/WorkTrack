package com.myapp.worktrack.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.myapp.worktrack.Database;
import com.myapp.worktrack.room.dao.TodoDao;
import com.myapp.worktrack.room.entitiy.TodoEntity;

import java.util.List;

public class TodoRepository {
  private TodoDao todoDao;

  public TodoRepository(Application application) {
    Database database = Database.getDatabaseInstance(application);
    this.todoDao = database.todoDao();
  }

  public LiveData<List<TodoEntity>> getTodoTasksList() {
    return todoDao.fetchAllTodoTask();
  }

  public void insertTask(TodoEntity todoEntity) {
    new TodoRepository.InsertAsyncTask(todoDao).execute(todoEntity);
  }

  public void deleteTasks(TodoEntity todoEntity) {
    new TodoRepository.DeleteAsyncTasks(todoDao).execute(todoEntity);

  }

  public void updateTask(TodoEntity todoEntity) {
    new TodoRepository.UpdateAsyncTask(todoDao).execute(todoEntity);
  }

  private static class InsertAsyncTask extends AsyncTask<TodoEntity, Void, Void> {
    private TodoDao todoDao;

    private InsertAsyncTask(TodoDao todoDao) {
      this.todoDao = todoDao;
    }

    @Override
    protected Void doInBackground(TodoEntity... todoEntities) {
      todoDao.insertTask(todoEntities[0]);
      return null;
    }
  }

  private static class DeleteAsyncTasks extends AsyncTask<TodoEntity, Void, Void> {
    private TodoDao todoDao;

    private DeleteAsyncTasks(TodoDao todoDao) {
      this.todoDao = todoDao;
    }

    @Override
    protected Void doInBackground(TodoEntity... todoEntities) {
      todoDao.delete(todoEntities[0]);
      return null;
    }
  }

  private static class UpdateAsyncTask extends AsyncTask<TodoEntity, Void, Void> {
    private TodoDao todoDao;

    public UpdateAsyncTask(TodoDao todoDao) {
      this.todoDao = todoDao;
    }

    @Override
    protected Void doInBackground(TodoEntity... todoEntities) {
      todoDao.update(todoEntities[0]);
      return null;
    }
  }
}
