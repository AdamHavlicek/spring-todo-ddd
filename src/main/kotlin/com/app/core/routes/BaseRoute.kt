package com.app.core.routes

import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

interface BaseRoute {
    operator fun invoke(): RouterFunction<ServerResponse>
}