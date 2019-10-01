package com.hexagonkt.todokt.backend

import com.hexagonkt.helpers.toStream
import com.hexagonkt.http.Method
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.Json

/** Store for tasks. */
//val store = MongoDbStore(Task::class, Task::code, "")

fun main(vararg args: String) {
    var task: Task? = null
    val server = Server(JettyServletAdapter()) {
        before {
            response.setHeader("access-control-allow-origin", "*")
            response.setHeader("access-control-allow-headers", "*")
        }

        options {
            response.setHeader("Allow", "${Method.GET}, ${Method.OPTIONS}, ${Method.POST}, ${Method.DELETE}")
        }

        // Serves 'resources/public' classpath folder from servers root '/'
        assets("public")

        // Renders index page from Pebble template. SPA which code is generated by Frontend module
        get {
            if (task != null) {
                ok(task!!, Json)
            } else {
                ok()
            }
        }
        post {
            task = Json.parse(request.body.toStream(), Task::class)
            ok(task!!, Json)
        }
        delete {
            task = null
            ok()
        }
    }

    server.start()
}
