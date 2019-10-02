package de.hartrampf.todobackend

import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter

fun main() {
    val taskRepository = InMemoryTaskRepository()
    val taskService = TaskService(taskRepository)
    val server = Server(JettyServletAdapter()) {
        Routing(taskService).defineRoutes(this)
    }

    server.start()
}
