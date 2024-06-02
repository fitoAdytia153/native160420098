package com.ubaya.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.todoapp.R
import com.ubaya.todoapp.databinding.FragmentTodoListBinding
import com.ubaya.todoapp.viewmodel.DetailTodoViewModel
import com.ubaya.todoapp.viewmodel.ListTodoViewModel

class TodoListFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel
    private lateinit var viewModelDetail: DetailTodoViewModel
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModelDetail = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        todoListAdapter = TodoListAdapter(arrayListOf(), viewModelDetail) { item ->
            viewModel.clearTask(item)
        } // Perubahan 2: Inisialisasi todoListAdapter di sini

        viewModel.refresh()

        setupRecyclerView()
        setupFab()

        observeViewModel()
    }

    private fun setupRecyclerView() {
        todoListAdapter = TodoListAdapter(arrayListOf(), viewModelDetail) { item ->
            viewModel.clearTask(item)
        }
        binding.recViewTodo.layoutManager = LinearLayoutManager(context)
        binding.recViewTodo.adapter = todoListAdapter
    }

    private fun setupFab() {
        binding.btnFab.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer { todoList ->
            todoListAdapter.updateTodoList(todoList)
            if (todoList.isEmpty()) {
                binding.recViewTodo.visibility = View.GONE
                binding.txtError.text = getString(R.string.todo_empty_message)
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.recViewTodo.visibility = View.VISIBLE
                binding.txtError.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.progressLoad.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.todoLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            binding.txtError.visibility = if (isError) View.VISIBLE else View.GONE
        })
    }
}
