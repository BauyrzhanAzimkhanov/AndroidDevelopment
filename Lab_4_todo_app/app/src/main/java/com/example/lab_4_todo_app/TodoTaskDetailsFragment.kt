package com.example.lab_4_todo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_4_todo_app.databinding.ActivityMainBinding
import org.w3c.dom.Text

class TodoTaskDetailsFragment : Fragment() {

    lateinit var todoTask: TodoTask
    lateinit var description: TextView
    lateinit var status: CheckBox
    lateinit var duration: TextView
    lateinit var title: TextView
    lateinit var category: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
            todoTask = savedInstanceState.getParcelable<TodoTask>(TODO_TASK)!!
        }
        var bundle: Bundle? = this.arguments
        if(bundle != null && !bundle.isEmpty) {
           todoTask = bundle.getParcelable<TodoTask>(TODO_TASK)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_task_details, container, false)
        initUI(view)
        title.text = todoTask.title.toString()
        description.text = todoTask.description
        status.isChecked = todoTask.status
        duration.text = todoTask.duration
        category.text = todoTask.category?.title
        return view
    }

    private fun initUI(view: View) {
        title = view.findViewById(R.id.titleTextViewDetailed) as TextView
        category = view.findViewById(R.id.categoryTextViewDetailed) as TextView
        duration = view.findViewById(R.id.durationTextViewDetailed) as TextView
        description = view.findViewById(R.id.descriptionTextViewDetailed) as TextView
        status = view.findViewById(R.id.statusCheckBoxDetailed) as CheckBox
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(TODO_TASK, todoTask)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TodoTaskDetailsFragment()
        private const val TODO_TASK = "todoTask"
    }


}