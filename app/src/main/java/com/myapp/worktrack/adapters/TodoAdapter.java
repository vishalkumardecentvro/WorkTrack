package com.myapp.worktrack.adapters;

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

import com.myapp.worktrack.R;
import com.myapp.worktrack.databinding.RvDoingBinding;
import com.myapp.worktrack.room.entitiy.DoingEntity;
import com.myapp.worktrack.room.entitiy.TodoEntity;
import com.myapp.worktrack.room.view.DoingView;
import com.myapp.worktrack.room.view.TodoView;

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

      //binding.llCardHeader.setBackground(context.getResources().getDrawable(R.drawable.todo_card_header));
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

    private void populate(TodoEntity todoEntity) {
      binding.tvName.setText(todoEntity.getTaskName());
      binding.tvPriority.setText(String.valueOf(todoEntity.getPriority()));
      binding.tvDate.setText(todoEntity.getDate());
      binding.tvTime.setText(todoEntity.getTime());
    }

    private void processDeleteDoingTask(int position) {
      delete(position);

      taskCompletedDialog = new Dialog(context);
      taskCompletedDialog.setContentView(R.layout.todo_dialog);
      taskCompletedDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      taskCompletedDialog.show();

      Button okay = taskCompletedDialog.findViewById(R.id.buttonOkay);

      okay.setOnClickListener(v -> taskCompletedDialog.dismiss());
    }

    private void delete(int position) {
      DoingView doingView = ViewModelProviders.of((FragmentActivity) context).get(DoingView.class);
      TodoView todoView = ViewModelProviders.of((FragmentActivity) context).get(TodoView.class);

      TodoEntity todoEntity = new TodoEntity();
      DoingEntity doingEntity = new DoingEntity();

      todoEntity.setTaskName(todoEntityList.get(position).getTaskName());
      todoEntity.setId(todoEntityList.get(position).getId());
      todoEntity.setDate(todoEntityList.get(position).getDate());
      todoEntity.setTime(todoEntityList.get(position).getTime());
      todoEntity.setPriority(todoEntityList.get(position).getPriority());

      doingEntity.setTaskName(todoEntityList.get(position).getTaskName());
      doingEntity.setDate(todoEntityList.get(position).getDate());
      doingEntity.setTime(todoEntityList.get(position).getTime());
      doingEntity.setPriority(todoEntityList.get(position).getPriority());

      doingView.insert(doingEntity);
      todoView.delete(todoEntity);
      Toast.makeText(context, "Task shifted to Doing section", Toast.LENGTH_SHORT).show();
    }
  }

}
