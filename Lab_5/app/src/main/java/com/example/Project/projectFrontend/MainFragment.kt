package com.example.Project.projectFrontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Project.R
import com.example.Project.projectFragments.ParentFragment
import org.koin.android.ext.android.inject

class MainFragment : ParentFragment() {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by inject()
    private val todoViewModel: TodoViewModel by inject()

    private lateinit var btnAdd: Button
    private lateinit var btnDeleteAll: Button
    private lateinit var rvTodos: RecyclerView

    private val todosAdapter by lazy {
        TodosAdapter(itemClickListener = viewModel.onItemClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    private fun setAdapter() {
        rvTodos.adapter = todosAdapter
    }

    override fun bindViews(view: View) = with(view) {    // we initialize our buttons here
        navController = Navigation.findNavController(this)
        btnAdd = view.findViewById(R.id.btn_add)
        btnDeleteAll = view.findViewById(R.id.btn_delete_all)

        rvTodos = findViewById(R.id.rvTodos)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvTodos.layoutManager = linearLayoutManager

        btnAdd.setOnClickListener{
            navController.navigate(
                R.id.action_mainFragment_to_addTodoFragment,
                Bundle.EMPTY
            )
        }

        btnDeleteAll.setOnClickListener{
            viewModel.deleteAll()
            todosAdapter.clearAll()
        }
    }

    override fun setData() {
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.getAllFromApi.observe(viewLifecycleOwner,
            Observer {    // we get data from backend
                todosAdapter.addItems(it)
            })
        viewModel.getAllFromDb.observe(viewLifecycleOwner,
            Observer {
                todosAdapter.clearAll()
                todosAdapter.addItems(it)
            })

        todoViewModel.liveData.observe(
            viewLifecycleOwner,    // we get data from view model by live data
            Observer { result ->
                when (result) {
                    is TodoViewModel.State.Result -> {
                        result.todos?.let { viewModel.filterByUserId(it) }
                    }
                    is TodoViewModel.State.Error -> {
                    }
                    else -> {}
                }
            }
        )
        viewModel.isDatabaseEmpty.observe(viewLifecycleOwner,
            Observer {
                if (it.isNullOrEmpty()) {
                    todoViewModel.getTodosFromBack()
                } else {
                    viewModel.initRecyclerList()
                }
            })

    }
}