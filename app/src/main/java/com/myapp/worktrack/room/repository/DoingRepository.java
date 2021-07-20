package com.myapp.worktrack.room.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.myapp.worktrack.Database;
import com.myapp.worktrack.room.dao.DoingDao;
import com.myapp.worktrack.room.entitiy.DoingEntity;

import java.util.List;

public class DoingRepository {
  private DoingDao doingDao;

  public DoingRepository(Application application) {
    Database database = Database.getDatabaseInstance(application);
    this.doingDao = database.doingDao();
  }

  public LiveData<List<DoingEntity>> getDoingTasksList(){
    return doingDao.fetchAllDoingTask();
  }

  public void insertTask(DoingEntity doingEntity) {
    new InsertAsyncTask(doingDao).execute(doingEntity);
  }

  public void deleteTasks(DoingEntity doingEntity) {
    new DeleteAsyncTasks(doingDao).execute(doingEntity);

  }

  public void updateTask(DoingEntity doingEntity) {
    new UpdateAsyncTask(doingDao).execute(doingEntity);
  }

  private static class InsertAsyncTask extends AsyncTask<DoingEntity, Void, Void> {
    private DoingDao doingDao;

    private InsertAsyncTask(DoingDao doingDao) {
      this.doingDao = doingDao;
    }

    @Override
    protected Void doInBackground(DoingEntity... doingEntities) {
      doingDao.insertTask(doingEntities[0]);
      return null;
    }
  }

  private static class DeleteAsyncTasks extends AsyncTask<DoingEntity, Void, Void> {
    private DoingDao doingDao;

    private DeleteAsyncTasks(DoingDao doingDao) {
      this.doingDao = doingDao;
    }

    @Override
    protected Void doInBackground(DoingEntity... doingEntities) {
      doingDao.delete(doingEntities[0]);
      return null;
    }
  }

  private static class UpdateAsyncTask extends AsyncTask<DoingEntity, Void, Void> {
    private DoingDao doingDao;

    public UpdateAsyncTask(DoingDao doingDao) {
      this.doingDao = doingDao;
    }

    @Override
    protected Void doInBackground(DoingEntity... doingEntities) {
      doingDao.update(doingEntities[0]);
      return null;
    }
  }
}
