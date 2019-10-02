package de.hartrampf.todobackend

interface TaskRepository {
    fun getAllTasks(): List<Task>
    fun addTask(task: Task)
    fun removeAllTasks()
    fun getTaskById(id: Int): Task?
    fun removeTaskById(id: Int): Task?
    fun updateTask(idOfTaskToBeUpdated: Int, taskWithChanges: Task): Task?
}