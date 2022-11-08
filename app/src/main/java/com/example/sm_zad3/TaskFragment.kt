package com.example.sm_zad3

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
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
    private lateinit var categorySpinner: Spinner
    private val calendar = Calendar.getInstance()
    private var dateField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskId: UUID? = arguments?.getSerializable(ARG_TASK_ID) as UUID?
        task = TaskStorage.getInstance().getTask(taskId)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        doneCheckBox = view.findViewById(R.id.task_done)
        doneCheckBox.isChecked = task.isDone()
        doneCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.setDone(isChecked)
        }

        dateField = view.findViewById(R.id.task_date)
        dateField?.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);
                    setupDateFieldValue(calendar.time);
                    task.setDate(calendar.time);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        setupDateFieldValue(task.getDate())

        val categorySpinner = view.findViewById<Spinner>(R.id.task_category)
        categorySpinner.adapter =
            context?.let { ArrayAdapter(it, R.id.task_category, Category.values()) }
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                task.setCategory(Category.values()[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        categorySpinner.setSelection(task.getCategory().ordinal)


        return view
    }

    private fun setupDateFieldValue(date: Date) {
        val locale = Locale("pl", "PL")
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", locale)
        dateField?.setText(dateFormat.format(date))
    }
}