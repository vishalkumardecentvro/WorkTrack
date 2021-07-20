package com.example.worktrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktrack.AddTodoTaskBottomSheetDialog;
import com.example.worktrack.R;
import com.example.worktrack.adapters.DoingAdapter;
import com.example.worktrack.adapters.TodoAdapter;
import com.example.worktrack.databinding.AddTaskBottomSheetDialogBinding;
import com.example.worktrack.databinding.FragmentTodoBinding;
import com.example.worktrack.room.entitiy.TodoEntity;
import com.example.worktrack.room.view.TodoView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TodoFragment extends Fragment {
  private FragmentTodoBinding binding;
  private AddTodoTaskBottomSheetDialog addTodoTaskBottomSheetDialog;
  private TodoAdapter todoAdapter;
  private TodoView todoView;
  private List<TodoEntity> todoEntityList;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentTodoBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    return view;
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    instantiate();
    initialize();
    listen();
    load();
  }

  private void instantiate(){
    addTodoTaskBottomSheetDialog = new AddTodoTaskBottomSheetDialog(getContext(), AddTaskBottomSheetDialogBinding.inflate(getLayoutInflater()));
    todoAdapter = new TodoAdapter(getContext());
    todoView = ViewModelProviders.of(this).get(TodoView.class);

  }

  private void initialize(){
    //binding.llCreateTask.setBackgroundColor(getContext().getResources().getColor(R.color.focusTwo));
    binding.rvTodo.setAdapter(todoAdapter);
  }

  private void listen(){
    binding.llCreateTask.setOnClickListener(v -> processAddTask());

    todoAdapter.setOnEditClick(new TodoAdapter.OnEditClick() {
      @Override
      public void editTask(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("id", todoEntityList.get(position).getId());
        bundle.putString("taskName", todoEntityList.get(position).getTaskName());
        bundle.putString("taskDate", todoEntityList.get(position).getDate());
        bundle.putString("taskTime", todoEntityList.get(position).getTime());
        bundle.putInt("taskPriority", todoEntityList.get(position).getPriority());

        bundle.putBoolean("editMode", true);

        addTodoTaskBottomSheetDialog.show();
        addTodoTaskBottomSheetDialog.instantiateBundle(bundle);
      }
    });

  }

  private void load(){
    todoView.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<TodoEntity>>() {
      @Override
      public void onChanged(List<TodoEntity> todoEntities) {
        todoEntityList = todoEntities;
        todoAdapter.setTodoEntityList(todoEntities);
      }
    });

  }

  private void processAddTask() {
    addTodoTaskBottomSheetDialog.show();
  }
}