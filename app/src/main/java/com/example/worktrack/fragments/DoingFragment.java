package com.example.worktrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.worktrack.AddTaskBottomSheetDialog;
import com.example.worktrack.databinding.AddTaskBottomSheetDialogBinding;
import com.example.worktrack.databinding.FragmentDoingBinding;

import org.jetbrains.annotations.NotNull;

public class DoingFragment extends Fragment {

  private FragmentDoingBinding binding;
  private AddTaskBottomSheetDialog addTaskBottomSheetDialog;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentDoingBinding.inflate(inflater, container, false);
    View view = binding.getRoot();
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    instantiate();
    initialize();
    listen();
    load();
  }

  private void instantiate() {
    addTaskBottomSheetDialog = new AddTaskBottomSheetDialog(getActivity(), AddTaskBottomSheetDialogBinding.inflate(getLayoutInflater()));
  }

  private void initialize() {

  }

  private void listen() {
    binding.ivAddTask.setOnClickListener(v -> processAddTask());

  }

  private void load() {

  }

  private void processAddTask() {
    addTaskBottomSheetDialog.show();
  }
}