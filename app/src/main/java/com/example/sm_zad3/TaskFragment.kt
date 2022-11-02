package com.example.sm_zad3

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*


class TaskFragment : Fragment() {

    companion object {

        private val ARG_TASK_ID: String = "ARG_TASK_ID"

        fun newInstance(taskID: UUID?): TaskFragment {
            val bundle = Bundle()
            bundle.putSerializable(ARG_TASK_ID, taskID)
            val taskFragment = TaskFragment()
            taskFragment.arguments = bundle
            return taskFragment
        }
    }

    private lateinit var task: Task
    private lateinit var nameField: TextView
    private lateinit var dateButton: Button
    private lateinit var doneCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId: UUID? = arguments?.getSerializable(ARG_TASK_ID) as UUID?
        task = TaskStorage.getInstance().getTask(taskId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view: View = inflater.inflate(R.layout.fragment_task, container, false)

        nameField = view.findViewById(R.id.task_name)
        nameField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                task.setName(s.toString())
            }
            override fun afterTextChanged(s: Editable) {}
        })
        nameField.text = task.getName()

        dateButton = view.findViewById(R.id.task_date)
        dateButton.text = task.getDate().toString()
        dateButton.isEnabled = false

        doneCheckBox = view.findViewById(R.id.task_done)
        doneCheckBox.isChecked = task.isDone()
        doneCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.setDone(isChecked)
        }

        return view
    }
}