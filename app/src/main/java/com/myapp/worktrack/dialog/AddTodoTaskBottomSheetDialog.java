package com.myapp.worktrack.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.myapp.worktrack.databinding.AddTaskBottomSheetDialogBinding;
import com.myapp.worktrack.room.entitiy.TodoEntity;
import com.myapp.worktrack.room.view.TodoView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.isolpro.library.materialedittext.MaterialEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTodoTaskBottomSheetDialog extends BottomSheetDialog {
  private AddTaskBottomSheetDialogBinding binding;
  private Context context;
  private TodoView todoView;
  private Bundle bundle;
  private List<MaterialEditText> metInputs;
  private boolean editMode = false;

  public AddTodoTaskBottomSheetDialog(@NonNull @NotNull Context context, AddTaskBottomSheetDialogBinding binding) {
    super(context);
    this.context = context;
    this.binding = binding;
    clear();

    instantiate();
    initialize();
    listen();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void dismiss() {
    super.dismiss();
    clear();
  }

  private void instantiate() {
    metInputs = new ArrayList<>();

  }

  public void instantiateBundle(Bundle bundleInEditMode) {

    bundle = bundleInEditMode;
    if (bundle.containsKey("editMode")) {

      binding.metTaskName.setText(bundle.getString("taskName"));
      binding.metPriority.setText(String.valueOf(bundle.getInt("taskPriority")));
      binding.tvTime.setText(bundle.getString("taskTime"));
      binding.tvDate.setText(bundle.getString("taskDate"));

      editMode = bundle.getBoolean("editMode");
    }
  }

  private void initialize() {
    metInputs.add(binding.metTaskName);
    metInputs.add(binding.metPriority);

    setContentView(binding.getRoot());
    todoView = ViewModelProviders.of((FragmentActivity) context).get(TodoView.class);

  }

  private void listen() {
    binding.mcvSave.setOnClickListener(v -> processSaveTask());
    binding.mcvDate.setOnClickListener(v -> openDatePicker());
    binding.mcvTime.setOnClickListener(v -> openTimePicker());

  }

  private void processSaveTask() {
    if (!MaterialEditText.validateEditTexts(
            metInputs.toArray(new MaterialEditText[]{})
    )) return;

    if(Integer.parseInt(binding.metPriority.getString())< 1 ||Integer.parseInt(binding.metPriority.getString()) >10){
      Toast.makeText(context, "Priority should be between 1 and 10", Toast.LENGTH_SHORT).show();
      return;
    }

    if (binding.tvDate.getText().toString().isEmpty() || binding.tvTime.getText().toString().isEmpty()) {
      Toast.makeText(context, "Please enter date and time", Toast.LENGTH_SHORT).show();
      return;
    }

    TodoEntity todoEntity = new TodoEntity();

    todoEntity.setTaskName(binding.metTaskName.getString());
    todoEntity.setPriority(Integer.parseInt(binding.metPriority.getString()));
    todoEntity.setTime(binding.tvTime.getText().toString());
    todoEntity.setDate(binding.tvDate.getText().toString());

    if (editMode) {
      todoEntity.setId(bundle.getInt("id"));
      todoView.update(todoEntity);
      editMode = false;

    } else {
      todoView.insert(todoEntity);
    }

    clear();
    dismiss();
  }

  private void openDatePicker() {
    Calendar c = Calendar.getInstance();
    int dayOfMonth = c.get(Calendar.DATE);
    int month = c.get(Calendar.MONTH);
    int year = c.get(Calendar.YEAR);

    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        binding.tvDate.setText(dayOfMonth + "/" + month + "/" + year);
      }
    }, dayOfMonth, month, year);

    datePickerDialog.setTitle("Select Date");
    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    datePickerDialog.show();
  }

  private void openTimePicker() {

    Calendar time = Calendar.getInstance();
    int hour = time.get(Calendar.HOUR);
    int minute = time.get(Calendar.MINUTE);

    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (hourOfDay < 12)
          binding.tvTime.setText(hourOfDay + ":" + minute + " AM");
        else
          binding.tvTime.setText(hourOfDay + ":" + minute + " PM");

      }
    }, hour, minute, false);

    timePickerDialog.setTitle("Select Time");
    timePickerDialog.show();
  }

  public void clear() {
    binding.metPriority.clear();
    binding.metTaskName.clear();
    binding.tvDate.setText("");
    binding.tvTime.setText("");
  }
}
