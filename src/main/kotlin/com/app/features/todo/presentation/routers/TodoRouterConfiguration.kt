package com.app.features.todo.presentation.routers

import com.app.features.todo.presentation.routes.GetTodosRoute
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class TodoRouterConfiguration(
    private val getTodosRoute: GetTodosRoute,
) {
    @Value("\${springdoc.version}")
    lateinit var version: String

    @Bean
    fun todoRouter(
    ) = router {
        path("/v$version/todos").nest {
            this.add(
                getTodosRoute()
            )

        }
    }


}