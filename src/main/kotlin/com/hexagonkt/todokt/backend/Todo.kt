package com.hexagonkt.todokt.backend

import com.hexagonkt.helpers.toStream
import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.Json

fun main() {
    val taskRepository: TasksRepository = TasksInMemoryRepository()
    val server = Server(JettyServletAdapter()) {
        before {
            allowCors()
        }

        options { }

        get {
            ok(taskRepository.getAllTasks(), Json)
        }
        get("/task/{id}") {
            val task = taskRepository.getTaskById(pathParameters["id"].toInt())
            if (task != null) {
                ok(task, Json)
            } else {
                send(404, "Task not found")
            }
        }
        post {
            val task = Json.parse(request.body.toStream(), Task::class)
            taskRepository.addTask(task)
            ok(task, Json)
        }
        delete("/task/{id}") {
            val task = taskRepository.removeTaskById(pathParameters["id"].toInt())
            if (task != null) {
                ok("Task deleted")
            } else {
                send(404, "Task not found")
            }
        }
        delete {
            taskRepository.removeAllTasks()
            ok()
        }
    }

    server.start()
}

private fun Call.allowCors() {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH")
    response.setHeader("Access-Control-Max-Age", "3600")
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept")
}
