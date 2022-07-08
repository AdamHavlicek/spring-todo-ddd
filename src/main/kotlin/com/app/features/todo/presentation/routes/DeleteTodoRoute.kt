package com.app.features.todo.presentation.routes

import com.app.core.failures.BadRequestFailure
import com.app.core.failures.NotFoundFailure
import com.app.core.routes.BaseRoute
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.usecases.DeleteTodoUseCase
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
class DeleteTodoRoute(
    private val deleteTodoUseCase: DeleteTodoUseCase,
) : BaseRoute {
    override fun invoke(): RouterFunction<ServerResponse> {
        return route().DELETE(
            "/{todoId}",
            accept(MediaType.APPLICATION_JSON),
            { request ->
                val todoId: Int = request.pathVariable("todoId").toInt()
                deleteTodoUseCase(todoId).fold(
                    ifLeft = { Mono.error(it) },
                    ifRight = { ServerResponse.ok().bodyValue(it) }
                )
            },
            { ops ->
                ops
                    .operationId("Delete Todo")
                    .tag("Todos")
                    .description("Delete a specific todo")
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
                            .description("Todo deleted")
                            .implementation(TodoReadModel::class.java)
                    )
                    .response(
                        ResponseBuilder.responseBuilder()
                            .responseCode("400")
                            .description("Todo already deleted")
                            .implementation(BadRequestFailure::class.java)
                    ).response(
                        ResponseBuilder.responseBuilder()
                            .responseCode("404")
                            .description("Todo not found")
                            .implementation(NotFoundFailure::class.java)
                    )
            }
        ).build()
    }
}