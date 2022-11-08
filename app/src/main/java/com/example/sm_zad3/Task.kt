package com.example.sm_zad3

import java.util.Date
import java.util.UUID

class Task {
    private var id: UUID = UUID.randomUUID()
    private var name: String? = null
    private var date: Date = Date()
    private var done: Boolean? = null
    private var category: Category = Category.HOME

    fun isDone(): Boolean {
        return done ?: false
    }

    fun setDone(done: Boolean) {
        this.done = done
    }

    fun getDate(): Date {
        return date
    }

    fun setDate(date: Date) {
        this.date = date
    }

    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }

    fun getUUID(): UUID {
        return id
    }

    fun getName(): String? {
        return name
    }

    fun setCategory(cat: Category) {
        this.category = cat
    }

    fun getCategory(): Category {
        return this.category
    }

    fun getId(): UUID {
        return this.id
    }
}