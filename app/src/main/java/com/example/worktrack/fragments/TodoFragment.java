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
    binding.llCreateTask.setBackgroundColor(getContext().getResources().getColor(R.color.focusTwo));
    binding.rvTodo.setAdapter(todoAdapter);
  }

  private void listen(){
    binding.llCreateTask.setOnClickListener(v -> processAddTask());

  }

  private void load(){
    todoView.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<TodoEntity>>() {
      @Override
      public void onChanged(List<TodoEntity> todoEntities) {
        todoAdapter.setTodoEntityList(todoEntities);
      }
    });

  }

  private void processAddTask() {
    addTodoTaskBottomSheetDialog.show();
  }
}