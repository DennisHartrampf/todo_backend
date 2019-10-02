package com.hexagonkt.todokt.backend

import java.util.concurrent.atomic.AtomicInteger

class TasksInMemoryRepository : TasksRepository {
    private val nextId = AtomicInteger()
    private val tasks: MutableMap<Int, Task> = mutableMapOf()

    override fun getAllTasks(): List<Task> {
        return tasks.values.toList()
    }

    override fun addTask(task: Task) {
        val id = nextId.incrementAndGet()
        task.id = id
        tasks[id] = task
    }

    override fun removeAllTasks() {
        tasks.clear()
    }

    override fun getTaskById(id: Int): Task? {
        return tasks[id]
    }

    override fun removeTaskById(id: Int): Task? {
        return tasks.remove(id)
    }
}