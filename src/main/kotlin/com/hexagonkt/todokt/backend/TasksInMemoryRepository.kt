package com.hexagonkt.todokt.backend

class TasksInMemoryRepository : TasksRepository {
    private val tasks: MutableList<Task> = mutableListOf()

    override fun getAllTasks(): List<Task> {
        return tasks
    }

    override fun addTask(task: Task) {
        tasks.add(task)
    }

    override fun removeAllTasks() {
        tasks.clear()
    }
}