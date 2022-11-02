package com.example.sm_zad3

import java.util.Date
import java.util.UUID

class Task {
    private var id: UUID
    private var name: String? = null
    private var date: Date
    private var done: Boolean? = null

    constructor(){
        this.id = UUID.randomUUID()
        this.date = Date()
    }

    fun isDone(): Boolean{
        return done ?: false
    }

    fun setDone(done: Boolean) {
        this.done = done
    }

    fun getDate(): Date {
        return date
    }

    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }

    fun getUUID(): UUID{
        return id
    }

    fun getName(): String?{
        return name
    }
}