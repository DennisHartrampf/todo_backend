package com.hexagonkt.todokt.backend

import com.hexagonkt.helpers.toStream
import com.hexagonkt.http.Method
import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.Json

fun main() {
    val taskRepository : TasksRepository = TasksInMemoryRepository()
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
        delete {
            taskRepository.removeAllTasks()
            ok()
        }
    }

    server.start()
}

private fun Call.allowCors() {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "${Method.GET}, ${Method.OPTIONS}, ${Method.POST}, ${Method.DELETE}, ${Method.PATCH}")
}
