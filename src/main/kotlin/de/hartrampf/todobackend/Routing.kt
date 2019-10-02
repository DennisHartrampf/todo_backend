package de.hartrampf.todobackend

import com.hexagonkt.helpers.toStream
import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Request
import com.hexagonkt.http.server.Router
import com.hexagonkt.serialization.Json
import kotlin.reflect.KClass

internal class Routing(private val taskService: TaskService) {

    fun defineRoutes(router: Router) {
        with(router) {
            defineOperationsForAllRoutes()
            defineOperationsForRootRoute()
            defineOperationsForTaskRoute()
        }
    }

    private fun Router.defineOperationsForAllRoutes() {
        before("/*") {
            allowCors()
        }

        options("/*") { }
    }

    private fun Call.allowCors() {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept")
    }

    private fun Router.defineOperationsForRootRoute() {
        defineGetOperationForRootRoute()
        definePostOperationForRootRoute()
        defineDeleteOperationForRootRoute()
    }

    private fun Router.defineGetOperationForRootRoute() {
        get {
            ok(taskService.getAllTasks(), Json)
        }
    }

    private fun Router.definePostOperationForRootRoute() {
        post {
            val task = request.getJsonBody(Task::class)
            taskService.addTask(task)
            ok(task, Json)
        }
    }

    private fun Request.getJsonBody(targetClass: KClass<Task>) = Json.parse(body.toStream(), targetClass)

    private fun Router.defineDeleteOperationForRootRoute() {
        delete {
            taskService.removeAllTasks()
            ok()
        }
    }

    private fun Router.defineOperationsForTaskRoute() {
        path("/task/{id}") {
            defineGetOperationForTaskRoute()
            definePatchOperationForTaskRoute()
            defineDeleteOperationForTaskRoute()
        }
    }

    private fun Router.defineGetOperationForTaskRoute() {
        get {
            val task = taskService.getTaskById(getIdParameter())
            if (task != null) {
                ok(task, Json)
            } else {
                sendTaskNotFound()
            }
        }
    }

    private fun Call.sendTaskNotFound() = send(404, "Task not found")

    private fun Call.getIdParameter() = pathParameters["id"].toInt()

    private fun Router.definePatchOperationForTaskRoute() {
        patch {
            val updatedTask = taskService.updateTask(getIdParameter(), request.getJsonBody(Task::class))
            if (updatedTask != null) {
                ok(updatedTask, Json)
            } else {
                sendTaskNotFound()
            }
        }
    }

    private fun Router.defineDeleteOperationForTaskRoute() {
        delete {
            val task = taskService.removeTaskById(getIdParameter())
            if (task != null) {
                ok("Task deleted")
            } else {
                sendTaskNotFound()
            }
        }
    }
    
}

