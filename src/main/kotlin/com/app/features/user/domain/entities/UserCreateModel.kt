package com.app.features.user.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.Email
import javax.validation.constraints.Size
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Schema(description = "User create DTO")
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserCreateModel(
    @field:Email
    @field:NotEmpty
    @field:Size(min = 3, max = 24)
    @Schema(description = "User's email", example = "test@test.com")
    val email: String,
    @field:NotEmpty
    @field:NotNull
    @field:Size(min = 8, max = 24)
    @Schema(description = "User's password", example = "password")
    val password: String,
)