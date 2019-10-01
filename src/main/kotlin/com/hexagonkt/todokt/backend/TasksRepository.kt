package com.hexagonkt.todokt.backend

interface TasksRepository {
    fun getAllTasks(): List<Task>
    fun addTask(task: Task)
    fun removeAllTasks()
    fun getTaskById(id: Int): Task?
}