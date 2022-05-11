package com.example.Project.projectFrontend

import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.Project.R
import com.example.Project.projectFragments.ParentViewHolder
import com.example.Project.projectData.clickHandlers.ItemClickListener
import com.example.Project.projectData.models.ActionType
import com.example.Project.projectData.models.Todo


class TodosAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val VIEW_TYPE_ERROR = 0
    private val VIEW_TYPE_NORMAL = 1

    private val todos = ArrayList<Todo>()


    fun clearAll() {
        todos.clear()
        notifyDataSetChanged()
    }


    fun addItems(list: List<Todo>) {
        todos.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> TodosViewHolder(
                inflater.inflate(R.layout.adapter_todo, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Todo -> {
                return VIEW_TYPE_NORMAL
            }
            else -> {
                return VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int = todos.size

    fun getItem(position: Int): Todo? {
        return todos[position]
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        if (holder is TodosViewHolder) {
            val item = todos[position]
            holder.bind(item)
            holder.setItemClick(item)
            holder.setMenuClick(item)
        }
    }

    inner class TodosViewHolder(view: View) : ParentViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val cbState: CheckBox = view.findViewById(R.id.cb_state)
        private val vDots: View = view.findViewById(R.id.v_dots)

        fun bind(todo: Todo) {
            if(todo.completed){
                val spannableString1 = SpannableString(todo.title)
                spannableString1.setSpan(StrikethroughSpan(), 0, todo.title!!.length, 0)
                tvTitle.text = spannableString1
            }else{
                tvTitle.text = todo.title
            }
            cbState.isChecked = todo.completed

            vDots.setOnClickListener {
                var tempItem = todo
                val popup = PopupMenu(
                    itemView.context,
                    vDots,
                    R.style.PopupMenuStyle
                )
                popup.inflate(R.menu.menu_settings)
                popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        R.id.item_delete -> {
                            val copy = tempItem.copy(actionType = ActionType.DELETE.id)
                            itemClickListener.onItemMenuClick(copy)
                            Toast.makeText(
                                itemView.context,
                                "DELETED",
                                Toast.LENGTH_LONG
                            ).show()
                            true
                        }
                        else ->
                            false
                    }
                })

                popup.show()
            }

        }
        fun setItemClick(item: Todo) {
            cbState.setOnClickListener {
                itemClickListener.onItemClick(item)
            }
        }
        fun setMenuClick(item: Todo){
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

}