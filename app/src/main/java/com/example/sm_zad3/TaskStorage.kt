package com.example.sm_zad3

import android.util.Log
import java.util.*

class TaskStorage {
    companion object {
        private val taskStorage: TaskStorage = TaskStorage()
        fun getInstance(): TaskStorage {
            return taskStorage
        }
    }

    private var tasks: MutableList<Task> = mutableListOf()

    constructor() {
        for (i in 1..150) {
            var task: Task = Task()
            task.setName("Pilne zadanie numer " + i)
            task.setDone(i % 3 == 0)
            if (i % 3 == 0) {
                task.setCategory(Category.STUDIES)
            } else {
                task.setCategory(Category.HOME)
            }
            tasks.add(task)
        }
    }

    fun getTasks(): MutableList<Task> {
        return tasks
    }

    fun getTask(taskID: UUID?): Task {
        var taskFound = Task()
        for (task in tasks) {
            if (task.getUUID() == taskID) {
                taskFound = task
                Log.d("TASK_KEY", "Found task: " + taskFound.getName())
            }
        }
        return taskFound

    }

    fun addTask(task: Task) {
        this.tasks.add(task)
    }
}