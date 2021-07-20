package com.myapp.worktrack.room.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapp.worktrack.room.entitiy.DoingEntity;
import com.myapp.worktrack.room.repository.DoingRepository;

import java.util.List;

public class DoingView extends AndroidViewModel {

  private DoingRepository repository;

  public DoingView(@NonNull Application application) {
    super(application);
    repository = new DoingRepository(application);
  }

  public void insert(DoingEntity doingEntity) {
    repository.insertTask(doingEntity);
  }

  public void delete(DoingEntity doingEntity) {
    repository.deleteTasks(doingEntity);
  }

  public void update(DoingEntity doingEntity) {
    repository.updateTask(doingEntity);
  }

  public LiveData<List<DoingEntity>> getAllTask() {
    return repository.getDoingTasksList();
  }
}
