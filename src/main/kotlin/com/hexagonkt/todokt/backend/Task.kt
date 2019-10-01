package com.hexagonkt.todokt.backend

data class Task(
        var title: String = "",
        var order: Int = 0,
        var completed: Boolean = false,
        var url: String = ""
)