package com.example.sm_zad3

import androidx.fragment.app.Fragment

class TaskListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return TaskListFragment()
    }
}