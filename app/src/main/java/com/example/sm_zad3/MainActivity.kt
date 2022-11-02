package com.example.sm_zad3

import androidx.fragment.app.Fragment
import java.util.*


class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val taskId: UUID? = intent.getSerializableExtra(TaskListFragment.KEY_EXTRA_TASK_ID) as UUID?
        return TaskFragment.newInstance(taskId)
    }
}