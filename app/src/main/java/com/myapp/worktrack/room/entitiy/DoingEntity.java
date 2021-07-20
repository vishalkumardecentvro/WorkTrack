package com.myapp.worktrack.room.entitiy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "doing")
public class DoingEntity {

  private String taskName,date,time;
  private int priority;

  @PrimaryKey(autoGenerate = true)
  private int id;

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
