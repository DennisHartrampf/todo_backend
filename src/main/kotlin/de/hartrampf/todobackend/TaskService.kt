package de.hartrampf.todobackend

internal class TaskService(private val taskRepository: TaskRepository) {
    fun getAllTasks() = taskRepository.getAllTasks()

    fun addTask(task: Task) = taskRepository.addTask(task)

    fun removeAllTasks() = taskRepository.removeAllTasks()

    fun getTaskById(id: Int) = taskRepository.getTaskById(id)

    fun updateTask(idOfTaskToBeUpdated: Int, taskFromWhichToUpdate: Task): Task? {
        val existingTask = taskRepository.getTaskById(idOfTaskToBeUpdated)
        return if (existingTask != null) {
            taskRepository.updateTask(idOfTaskToBeUpdated, taskFromWhichToUpdate)
        } else {
            null
        }
    }

    fun removeTaskById(id: Int): Task? = taskRepository.removeTaskById(id)
}
