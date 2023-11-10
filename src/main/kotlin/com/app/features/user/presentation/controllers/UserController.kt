package com.app.features.user.presentation.controllers

import com.app.core.failures.BadRequestFailure
import com.app.core.failures.NotFoundFailure
import com.app.core.failures.ValidationFailure
import com.app.features.user.domain.entities.UserCreateModel
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.entities.UserUpdateModel
import com.app.features.user.domain.usecases.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.annotation.RequestScope
import java.net.URI
import jakarta.validation.Valid


@RestController
@RequestMapping("/v\${springdoc.version}/users")
@Tag(name = "Users", description = "The user api")
class UserController(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) {

    @Operation(description = "Get list of users")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Users found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = UserReadModel::class)),
                    )
                ],
            ),
            ApiResponse(
                responseCode = "404",
                description = "No Users found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = NotFoundFailure::class)
                    )
                ],
            )
        ]
    )
    @GetMapping
    fun getUsers(): List<UserReadModel> {
        val users = getUsersUseCase()
            .fold(
                ifLeft = {
                    throw it
                },
                ifRight = {
                    it
                }
            )

        return users
    }

    @Operation(description = "Create a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "User created",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserReadModel::class)
                    )
                ],
                headers = [
                    Header(
                        name = "location",
                        description = "Url of new user's detail",
                        required = true,
                        schema = Schema(
                            example = "<api_version>/users/1"
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User already exists",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = BadRequestFailure::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data in request body",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ValidationFailure::class)
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun createUser(
        request: ServerHttpRequest,
        @Valid
        @RequestBody
        data: UserCreateModel

    ): ResponseEntity<UserReadModel> {
        return createUserUseCase(data).fold(
            ifLeft = { throw it },
            ifRight = { ResponseEntity.created(
                URI("${request.path}/${it.id}")
            ).body(it) }
        )
    }

    @Operation(description = "Get a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserReadModel::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = BadRequestFailure::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/{userId}")
    fun getUser(
        @PathVariable
        userId: Int,
    ): UserReadModel {
        return getUserUseCase(userId).fold(
            ifLeft = { throw it },
            ifRight = { it }
        )
    }

    @Operation(description = "Delete a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User deleted",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserReadModel::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = NotFoundFailure::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User already is deleted",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = BadRequestFailure::class)
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/{userId}")
    fun deleteUser(
        @PathVariable
        userId: Int,
    ): UserReadModel {
        return deleteUserUseCase(userId).fold(
            ifLeft = { throw it },
            ifRight = { it }
        )
    }

    @Operation(description = "Partial update of a user")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "User Updated",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = UserReadModel::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = NotFoundFailure::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid data in request body",
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ValidationFailure::class)
                    )
                ]
            )
        ]
    )
    @PatchMapping("/{userId}")
    fun updateUser(
        @PathVariable
        userId: Int,
        @RequestBody
        @Valid
        data: UserUpdateModel,
    ): UserReadModel {
        return updateUserUseCase(userId to data).fold(
            ifLeft = { throw it },
            ifRight = { it }
        )
    }
}
