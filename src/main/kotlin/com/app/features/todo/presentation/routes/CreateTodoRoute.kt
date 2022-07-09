package com.app.features.todo.presentation.routes

import com.app.core.routes.BaseRoute
import com.app.features.todo.domain.entities.TodoCreateModel
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.usecases.CreateTodoUseCase
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI
import org.springdoc.core.fn.builders.apiresponse.Builder as ResponseBuilder
import org.springdoc.core.fn.builders.content.Builder as ContentBuilder
import org.springdoc.core.fn.builders.header.Builder as HeaderBuilder
import org.springdoc.core.fn.builders.requestbody.Builder as RequestBodyBuilder
import org.springdoc.core.fn.builders.schema.Builder as SchemaBuilder

@Component
class CreateTodoRoute(
    private val createTodoUseCase: CreateTodoUseCase,
) : BaseRoute {

    override fun invoke(): RouterFunction<ServerResponse> {
        return route().POST(
            "",
            accept(MediaType.APPLICATION_JSON),
            { request ->
                request.bodyToMono<TodoCreateModel>().flatMap { data ->
                    createTodoUseCase(data).fold(
                        ifLeft = { Mono.error(it) },
                        ifRight = {
                            ServerResponse
                                .created(
                                    URI("${request.path()}/${it.id}")
                                )
                                .bodyValue(it)
                        }
                    )
                }
            },
            { ops ->
                ops
                    .operationId("Create Todo")
                    .tag("Todos")
                    .description("Create a todo")
                    .requestBody(
                        RequestBodyBuilder
                            .requestBodyBuilder()
                            .description("Create todo DTO")
                            .content(
                                ContentBuilder.contentBuilder()
                                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                    .schema(
                                        SchemaBuilder
                                            .schemaBuilder()
                                            .implementation(TodoCreateModel::class.java)
                                    )
                            )
                    )
                    .response(
                        ResponseBuilder
                            .responseBuilder()
                            .responseCode("201")
                            .description("Todo created")
                            .header(
                                HeaderBuilder
                                    .headerBuilder()
                                    .name("location")
                                    .description("Url of new detail of todo")
                                    .required(true)
                                    .schema(
                                        SchemaBuilder
                                            .schemaBuilder()
                                            .example("<api_version>/todos/1")
                                    )
                            )
                            .implementation(TodoReadModel::class.java)
                    )
            }
        ).build()
    }
}