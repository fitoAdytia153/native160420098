package com.ubaya.todoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todoapp.databinding.TodoItemLayoutBinding
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel

class TodoListAdapter(
    private val todoList: ArrayList<Todo>,
    private val viewModelDetail: DetailTodoViewModel?,
    private val adapterOnClick: (Todo) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var binding: TodoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        with(holder.binding) {
            checkTask.text = todo.title

            checkTask.setOnCheckedChangeListener { _, isChecked ->
                val isDone = if (isChecked) 1 else 0
                viewModelDetail?.updateTodoStatus(todo.uuid, isDone)
            }

            imgEdit.setOnClickListener {
                val action = TodoListFragmentDirections.actionEditTodoFragment(todo.uuid)
                Navigation.findNavController(root).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }
}
