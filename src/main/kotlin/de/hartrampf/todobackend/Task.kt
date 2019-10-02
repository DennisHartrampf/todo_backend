package de.hartrampf.todobackend

data class Task(
        var title: String = "",
        var order: Int = 0,
        var completed: Boolean = false,
        var id: Int = -1
) {
    @Suppress("unused") // used in Json representation
    val url: String
        get() = "$SERVER_ROOT_URL/task/$id"

    companion object {
        const val SERVER_ROOT_URL = "https://dennis-hartrampf-todo-backend.herokuapp.com"
    }
}