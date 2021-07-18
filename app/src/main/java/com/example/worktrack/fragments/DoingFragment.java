package com.example.worktrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.worktrack.AddTaskBottomSheetDialog;
import com.example.worktrack.adapters.DoingAdapter;
import com.example.worktrack.databinding.AddTaskBottomSheetDialogBinding;
import com.example.worktrack.databinding.FragmentDoingBinding;
import com.example.worktrack.room.entitiy.DoingEntity;
import com.example.worktrack.room.view.DoingView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoingFragment extends Fragment {

  private FragmentDoingBinding binding;
  private AddTaskBottomSheetDialog addTaskBottomSheetDialog;
  private DoingView doingView;
  private DoingAdapter doingAdapter;

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
    doingView = ViewModelProviders.of(this).get(DoingView.class);
    doingAdapter = new DoingAdapter(getContext());
  }

  private void initialize() {
    binding.rvDoing.setAdapter(doingAdapter);

  }

  private void listen() {
    binding.llCreateTask.setOnClickListener(v -> processAddTask());
  }

  private void load() {
    doingView.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<DoingEntity>>() {
      @Override
      public void onChanged(List<DoingEntity> doingEntities) {
        doingAdapter.setDoingEntityList(doingEntities);
      }
    });
  }

  private void processAddTask() {
    addTaskBottomSheetDialog.show();
  }
}