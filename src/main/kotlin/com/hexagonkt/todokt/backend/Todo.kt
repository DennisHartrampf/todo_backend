package com.hexagonkt.todokt.backend

import com.hexagonkt.helpers.toStream
import com.hexagonkt.http.Method
import com.hexagonkt.http.server.Call
import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.Json

fun main() {
    var task: Task? = null
    val server = Server(JettyServletAdapter()) {
        before {
            allowCors()
        }

        options { }

        get {
            if (task != null) {
                ok(task!!, Json)
            } else {
                ok(arrayOf<String>(), Json)
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

private fun Call.allowCors() {
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "${Method.GET}, ${Method.OPTIONS}, ${Method.POST}, ${Method.DELETE}, ${Method.PATCH}")
}
