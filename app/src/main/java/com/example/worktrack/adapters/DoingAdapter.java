package com.example.worktrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktrack.R;
import com.example.worktrack.databinding.RvDoingBinding;
import com.example.worktrack.room.entitiy.DoingEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoingAdapter extends RecyclerView.Adapter<DoingAdapter.ViewHolder> {

  private List<DoingEntity> doingEntityList = new ArrayList<>();
  private Context context;
  private OnEditClick onEditClick;

  public DoingAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @NotNull
  @Override
  public DoingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    return new ViewHolder(RvDoingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onEditClick);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull DoingAdapter.ViewHolder holder, int position) {
    holder.populate(doingEntityList.get(position));

  }

  @Override
  public int getItemCount() {
    return doingEntityList.size();
  }

  public void setDoingEntityList(List<DoingEntity> doingEntityList) {
    this.doingEntityList = doingEntityList;
    notifyDataSetChanged();
  }

  public void setOnEditClick(OnEditClick onEditClick) {
    this.onEditClick = onEditClick;
  }

  public interface OnEditClick {
    void editTask(int position);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private RvDoingBinding binding;

    public ViewHolder(RvDoingBinding binding, OnEditClick onEditClick) {
      super(binding.getRoot());
      this.binding = binding;

      binding.ivEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (onEditClick != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
              onEditClick.editTask(position);
            }
          }
        }
      });
    }

    private void populate(DoingEntity doingEntity) {
      binding.tvName.setText(doingEntity.getTaskName());
      binding.tvPriority.setText(String.valueOf(doingEntity.getPriority()));
      binding.tvDate.setText(doingEntity.getDate());
      binding.tvTime.setText(doingEntity.getTime());

      ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.doingArray, android.R.layout.simple_spinner_item);
      spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
      binding.spinner.setAdapter(spinnerAdapter);
    }
  }

}
