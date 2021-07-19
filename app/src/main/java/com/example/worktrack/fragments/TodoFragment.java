package com.example.worktrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.worktrack.AddTodoTaskBottomSheetDialog;
import com.example.worktrack.databinding.AddTaskBottomSheetDialogBinding;
import com.example.worktrack.databinding.FragmentTodoBinding;

import org.jetbrains.annotations.NotNull;

public class TodoFragment extends Fragment {
  private FragmentTodoBinding binding;
  private AddTodoTaskBottomSheetDialog addTodoTaskBottomSheetDialog;

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

  }

  private void initialize(){

  }

  private void listen(){
    binding.llCreateTask.setOnClickListener(v -> processAddTask());

  }

  private void load(){

  }

  private void processAddTask() {
    addTodoTaskBottomSheetDialog.show();
  }
}