package com.app.features.todo.presentation.routes

import com.app.core.routes.BaseRoute
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.usecases.GetTodosUseCase
import org.springdoc.core.fn.builders.apiresponse.Builder
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class GetTodosRoute(
    private val getTodosUseCase: GetTodosUseCase,
) : BaseRoute {
    override operator fun invoke(): RouterFunction<ServerResponse> {
        return route()
            .GET(
                "/",
                accept(MediaType.APPLICATION_JSON),
                {
                    getTodosUseCase().fold(
                        ifLeft = { Mono.error(it) },
                        ifRight = { ServerResponse.ok().bodyValue(it) }
                    )
                },
                { ops ->
                    ops
                        .operationId("Todos")
                        .tag("Todos")
                        .description("Get a list of todos")
                        .response(
                            Builder
                                .responseBuilder()
                                .responseCode("200")
                                .description("list of todos")
                                .implementationArray(TodoReadModel::class.java)
                        )
                        .build()
                }
            ).build()
    }

}