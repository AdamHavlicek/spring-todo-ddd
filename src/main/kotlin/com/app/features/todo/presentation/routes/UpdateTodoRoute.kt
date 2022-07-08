package com.app.features.todo.presentation.routes

import com.app.core.failures.NotFoundFailure
import com.app.core.failures.ValidationFailure
import com.app.core.routes.BaseRoute
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.entities.TodoUpdateModel
import com.app.features.todo.domain.usecases.UpdateTodoUseCase
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import org.springdoc.core.fn.builders.apiresponse.Builder as ResponseBuilder
import org.springdoc.core.fn.builders.content.Builder as ContentBuilder
import org.springdoc.core.fn.builders.parameter.Builder as ParameterBuilder
import org.springdoc.core.fn.builders.requestbody.Builder as RequestBodyBuilder
import org.springdoc.core.fn.builders.schema.Builder as SchemaBuilder

@Component
class UpdateTodoRoute(
    private val updateTodoUseCase: UpdateTodoUseCase,
) : BaseRoute {
    override fun invoke(): RouterFunction<ServerResponse> {
        return route().PATCH(
            "/{todoId}",
            accept(MediaType.APPLICATION_JSON),
            { request ->
                val todoId: Int = request.pathVariable("todoId").toInt()
                request.bodyToMono<TodoUpdateModel>().flatMap { data ->
                    updateTodoUseCase(todoId to data).fold(
                        ifLeft = { Mono.error(it) },
                        ifRight = { ServerResponse.ok().bodyValue(it) }
                    )
                }
            },
            { ops ->
                ops
                    .operationId("Update Todo")
                    .tag("Todos")
                    .description("Update a specific todo")
                    .parameter(
                        ParameterBuilder.parameterBuilder()
                            .`in`(ParameterIn.PATH)
                            .name("todoId")
                            .description("Id of todo entity")
                            .implementation(Int::class.java)
                            .example("1")
                            .required(true)
                    )
                    .requestBody(
                        RequestBodyBuilder
                            .requestBodyBuilder()
                            .content(
                                ContentBuilder.contentBuilder()
                                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                    .schema(
                                        SchemaBuilder
                                            .schemaBuilder()
                                            .implementation(TodoUpdateModel::class.java)
                                    )
                            )
                            .description("Update todo DTO")
                            .required(true)
                    )
                    .response(
                        ResponseBuilder
                            .responseBuilder()
                            .responseCode("200")
                            .description("Todo updated")
                            .implementation(TodoReadModel::class.java)
                    )
                    .response(
                        ResponseBuilder
                            .responseBuilder()
                            .responseCode("404")
                            .description("Todo not found")
                            .implementation(NotFoundFailure::class.java)
                    )
                    .response(
                        ResponseBuilder
                            .responseBuilder()
                            .responseCode("400")
                            .description("Invalid data in request body")
                            .implementation(ValidationFailure::class.java)
                    )
            }
        ).build()
    }
}