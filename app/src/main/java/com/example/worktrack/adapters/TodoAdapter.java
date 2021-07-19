package com.example.worktrack.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worktrack.R;
import com.example.worktrack.databinding.RvDoingBinding;
import com.example.worktrack.room.entitiy.TodoEntity;
import com.example.worktrack.room.view.DoingView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

  private List<TodoEntity> todoEntityList = new ArrayList<>();
  private Context context;
  private OnEditClick onEditClick;
  private Dialog taskCompletedDialog;

  public TodoAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @NotNull
  @Override
  public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    return new ViewHolder(RvDoingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), onEditClick);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull TodoAdapter.ViewHolder holder, int position) {
    holder.populate(todoEntityList.get(position));

  }

  @Override
  public int getItemCount() {
    return todoEntityList.size();
  }

  public void setTodoEntityList(List<TodoEntity> todoEntityList) {
    this.todoEntityList = todoEntityList;
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

      binding.llCardHeader.setBackground(context.getResources().getDrawable(R.drawable.todo_card_header));
      binding.cbCheck.setText("Doing");

      binding.ivEdit.setOnClickListener(v -> {
        if (onEditClick != null) {
          int position = getAdapterPosition();
          if (position != RecyclerView.NO_POSITION) {
            onEditClick.editTask(position);
          }
        }
      });

      binding.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (isChecked) {
            processDeleteDoingTask(getAdapterPosition());
            buttonView.setChecked(false);
          }
        }
      });
    }

    private void populate(TodoEntity doingEntity) {
      binding.tvName.setText(doingEntity.getTaskName());
      binding.tvPriority.setText(String.valueOf(doingEntity.getPriority()));
      binding.tvDate.setText(doingEntity.getDate());
      binding.tvTime.setText(doingEntity.getTime());
    }

    private void processDeleteDoingTask(int position) {
      delete(position);

      taskCompletedDialog = new Dialog(context);
      taskCompletedDialog.setContentView(R.layout.doing_dialog);
      taskCompletedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      taskCompletedDialog.show();

      Button okay = taskCompletedDialog.findViewById(R.id.buttonOkay);

      okay.setOnClickListener(v -> taskCompletedDialog.dismiss());
    }

    private void delete(int position) {
      DoingView doingView = ViewModelProviders.of((FragmentActivity) context).get(DoingView.class);
      TodoEntity doingEntity = new TodoEntity();

      doingEntity.setTaskName(todoEntityList.get(position).getTaskName());
      doingEntity.setId(todoEntityList.get(position).getId());
      doingEntity.setDate(todoEntityList.get(position).getDate());
      doingEntity.setTime(todoEntityList.get(position).getTime());
      doingEntity.setPriority(todoEntityList.get(position).getPriority());

      //doingView.delete(doingEntity);
      Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
    }
  }

}