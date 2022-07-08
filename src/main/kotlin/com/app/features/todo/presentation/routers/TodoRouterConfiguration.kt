package com.app.features.todo.presentation.routers

import com.app.core.failures.BaseFailure
import com.app.core.failures.IBaseFailure
import com.app.features.todo.presentation.routes.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import kotlin.reflect.full.findAnnotations

@Configuration
class TodoRouterConfiguration(
    private val getTodosRoute: GetTodosRoute,
    private val getTodoRoute: GetTodoRoute,
    private val createTodoRoute: CreateTodoRoute,
    private val deleteTodoRoute: DeleteTodoRoute,
    private val updateTodoRoute: UpdateTodoRoute,
) {

    @Bean
    fun todoRouter(
        @Value("\${springdoc.version}")
        version: String
    ) = router {
        path("/v$version/todos").nest {
            arrayOf(
                getTodosRoute,
                getTodoRoute,
                createTodoRoute,
                deleteTodoRoute,
                updateTodoRoute
            ).map {
                this.add(it())
            }
            this.filter { request, function ->
                try {
                    function(request).onErrorResume {
                        this@TodoRouterConfiguration.handleException(it)
                    }
                } catch (exception: Throwable) {
                    this@TodoRouterConfiguration.handleException(exception)
                }
            }
        }
    }

    private fun handleException(exception: Throwable): Mono<ServerResponse> {
        val failure = BaseFailure.fromException(exception as Exception)()
        return ServerResponse
            .status(failure.getAnnotatedStatus())
            .bodyValue(failure)
    }


}