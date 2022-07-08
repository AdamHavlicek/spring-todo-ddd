package com.app.features.todo.presentation.routes

import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.usecases.GetTodosUseCase
import org.springdoc.core.fn.builders.apiresponse.Builder
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

@Component
class GetTodosRoute(
    private val getTodosUseCase: GetTodosUseCase
) {
     operator fun invoke(): RouterFunction<ServerResponse> {
        return route()
            .GET(
            "",
            accept(MediaType.APPLICATION_JSON),
            {
                getTodosUseCase().fold(
                    ifLeft = { throw it },
                    ifRight = { ServerResponse.ok().bodyValue(it) }
                )
            },
            { ops ->
                ops
                    .operationId("Todos")
                    .tag("Todos")
                    .response(
                        Builder
                            .responseBuilder().apply {
                                this.responseCode("200")
                                .description("Get a list of todos")
                                .implementationArray(TodoReadModel::class.java)
                            }

                    ).build()
            }
        ).build()
    }

}