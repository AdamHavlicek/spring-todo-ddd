package com.app.features.todo.presentation.routes

import com.app.core.failures.NotFoundFailure
import com.app.core.routes.BaseRoute
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.usecases.GetTodoUseCase
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import org.springdoc.core.fn.builders.apiresponse.Builder as ResponseBuilder
import org.springdoc.core.fn.builders.parameter.Builder as ParameterBuilder

@Component
class GetTodoRoute(
    private val getTodoUseCase: GetTodoUseCase,
) : BaseRoute {

    override operator fun invoke(): RouterFunction<ServerResponse> {
        return route().GET(
            "/{todoId}",
            accept(MediaType.APPLICATION_JSON),
            { request ->
                val todoId: Int = request.pathVariable("todoId").toInt()
                getTodoUseCase(todoId).fold(
                    ifLeft = { Mono.error(it) },
                    ifRight = { ServerResponse.ok().bodyValue(it) }
                )
            },
            { ops ->
                ops
                    .operationId("Todo")
                    .tag("Todos")
                    .description("Get a specific todo")
                    .parameter(
                        ParameterBuilder.parameterBuilder()
                            .`in`(ParameterIn.PATH)
                            .name("todoId")
                            .description("Id of todo entity")
                            .implementation(Int::class.java)
                            .example("1")
                            .required(true)
                    )
                    .response(
                        ResponseBuilder.responseBuilder()
                            .responseCode("200")
                            .description("Get specific todo")
                            .implementation(TodoReadModel::class.java)
                    )
                    .response(
                        ResponseBuilder.responseBuilder()
                            .responseCode("404")
                            .description("Todo not found")
                            .implementation(NotFoundFailure::class.java)
                    )
            }
        ).build()

    }

}