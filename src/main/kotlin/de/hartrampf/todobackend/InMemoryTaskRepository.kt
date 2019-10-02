package de.hartrampf.todobackend

import java.util.concurrent.atomic.AtomicInteger

class InMemoryTaskRepository : TaskRepository {
    private val nextId = AtomicInteger()
    private val tasks: MutableMap<Int, Task> = mutableMapOf()

    override fun getAllTasks(): List<Task> = tasks.values.toList()

    override fun addTask(task: Task) {
        val id = nextId.incrementAndGet()
        task.id = id
        tasks[id] = task
    }

    override fun removeAllTasks() = tasks.clear()

    override fun getTaskById(id: Int): Task? = tasks[id]

    override fun removeTaskById(id: Int): Task? = tasks.remove(id)

    override fun updateTask(idOfTaskToBeUpdated: Int, taskWithChanges: Task): Task? {
        val knownTask = tasks[idOfTaskToBeUpdated]
        knownTask?.updateBy(taskWithChanges)
        return knownTask
    }

    private fun Task.updateBy(taskWithChanges: Task) {
        with(this) {
            title = taskWithChanges.title
            order = taskWithChanges.order
            completed = taskWithChanges.completed
        }
    }
}