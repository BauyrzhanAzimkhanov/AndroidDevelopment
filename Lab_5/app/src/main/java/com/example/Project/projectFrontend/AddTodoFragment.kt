package com.example.Project.projectFrontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.Project.R
import com.example.Project.projectFragments.ParentFragment
import com.example.Project.projectData.models.Todo
import com.example.Project.projectFrontend.MainViewModel.Companion.USER_ID
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject

class AddTodoFragment : ParentFragment() {
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by inject()

    private lateinit var btnAdd: Button
    private lateinit var btnBack: Button
    private lateinit var etTitle: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    override fun bindViews(view: View) = with(view) {
        navController = Navigation.findNavController(this)
        etTitle = view.findViewById(R.id.et_title)
        btnAdd = view.findViewById(R.id.btn_create)
        btnBack = view.findViewById(R.id.btn_back)

        btnAdd.setOnClickListener {
            viewModel.addToDatabase(createTodo(etTitle.text.toString()))
            etTitle.setText("")
        }
        btnBack.setOnClickListener {
            navController.popBackStack()
        }

    }
    fun createTodo(title: String): Todo{
        val todo = Todo(id = lastIdx + 1, userId = USER_ID, title = title)
        return todo
    }

    override fun setData() {
        observeViewModel()
    }

    private fun setAdapter() {
    }

    var lastIdx: Int = 1000
    private fun observeViewModel() {
        viewModel.getAllFromDb.observe(viewLifecycleOwner,
            Observer {
                lastIdx = it.last().id
            })
    }

}