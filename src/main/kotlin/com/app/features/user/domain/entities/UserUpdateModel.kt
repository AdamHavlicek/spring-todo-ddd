package com.app.features.user.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "User update DTO")
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserUpdateModel(
    @field:Email
    @field:Size(min = 3, max = 24)
    @Schema(description = "User's email", example = "test@test.com")
    val email: String?,
    @field:Size(min = 8, max = 24)
    @Schema(description = "User's password", example = "password")
    val password: String?,
    val isActive: Boolean?,
    val isDeleted: Boolean?,
)