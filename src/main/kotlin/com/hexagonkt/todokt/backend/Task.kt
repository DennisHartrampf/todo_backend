package com.hexagonkt.todokt.backend

data class Task(
        var title: String = "",
        var order: Int = 0,
        var completed: Boolean = false,
        var id: Int = -1
) {
    val url: String
        get() = "$SERVER_ROOT_URL/task/$id"
    
    companion object {
//        const val SERVER_ROOT_URL = "http://10.241.158.132:2010"
        const val SERVER_ROOT_URL = "https://dennis-hartrampf-todo-backend.herokuapp.com"
    }
}