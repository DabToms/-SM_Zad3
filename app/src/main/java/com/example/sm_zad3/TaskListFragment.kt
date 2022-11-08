package com.example.sm_zad3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TaskListFragment : Fragment() {

    companion object {
        val KEY_EXTRA_TASK_ID: String = "KEY_EXTRA_TASK_ID"
    }

    private var recyclerView: RecyclerView? = null
    private var adapter: TaskAdapter? = null
    private var subtitleVisible: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_task_list, container, false)
        recyclerView = view.findViewById(R.id.task_recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        updateView()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateView() {
        val taskStorage = TaskStorage.getInstance()
        val tasks: List<Task> = taskStorage.getTasks()

        if (adapter == null) {
            adapter = TaskAdapter(tasks)
            recyclerView?.adapter = adapter
        } else {
            adapter?.notifyDataSetChanged()
        }
        updateSubtitle()
    }

    override fun onResume() {
        super.onResume()
        updateView()
    }

    fun updateSubtitle() {
        val taskStorage = TaskStorage.getInstance()
        val tasks: List<Task> = taskStorage.getTasks()
        var todoTasksCount = 0
        for (task in tasks) {
            if (!task.isDone()) {
                todoTasksCount++
            }
        }
        var subtitle: String? = getString(R.string.subtitle_format, todoTasksCount)
        if (!subtitleVisible) {
            subtitle = null
        }
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.supportActionBar!!.setSubtitle(subtitle)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_task_menu, menu)
        val subtitleItem: MenuItem = menu.findItem(R.id.show_subtitle)
        if (subtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                val task = Task()
                TaskStorage.getInstance().addTask(task)
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra(KEY_EXTRA_TASK_ID, task.getId())
                startActivity(intent)
                true
            }
            R.id.show_subtitle -> {
                subtitleVisible = !subtitleVisible
                requireActivity().invalidateOptionsMenu()
                updateSubtitle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class TaskHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_task, parent, false)),
        View.OnClickListener {
        private var task: Task? = null
        private val nameTextView: TextView
        private val dateTextView: TextView
        private var iconImageView: ImageView
        private var checkBox: CheckBox

        init {
            itemView.setOnClickListener(this)
            nameTextView = itemView.findViewById(R.id.task_item_name)
            dateTextView = itemView.findViewById(R.id.task_item_date)
            iconImageView = itemView.findViewById(R.id.task_item_icon)
            checkBox = itemView.findViewById(R.id.task_is_done)
        }

        fun bind(task: Task) {
            this.task = task
            nameTextView.text = task.getName()
            dateTextView.text = task.getDate().toString()
            if (task != null) {
                if (task.getCategory() == Category.HOME) {
                    iconImageView.setImageResource(R.drawable.ic_house)
                } else {
                    iconImageView.setImageResource(R.drawable.ic_unversity)
                }
                checkBox.isChecked = task.isDone()
            }
        }

        override fun onClick(view: View?) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(KEY_EXTRA_TASK_ID, task?.getUUID())
            startActivity(intent)
        }

        fun getCheckBox(): CheckBox? {
            return checkBox
        }
    }

    inner class TaskAdapter() :
        RecyclerView.Adapter<TaskHolder>() {

        private lateinit var tasks: List<Task>

        constructor(tasks: List<Task>) : this() {
            this.tasks = tasks
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val layoutInflater: LayoutInflater = LayoutInflater.from(activity)
            return TaskHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
            var checkBox: CheckBox? = holder.getCheckBox()

            if (checkBox != null) {
                checkBox.isChecked = tasks[position].isDone()
                checkBox.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
                    tasks[holder.adapterPosition].setDone(isChecked)
                }
            }
        }

        override fun getItemCount(): Int {
            return tasks.size
        }
    }
}