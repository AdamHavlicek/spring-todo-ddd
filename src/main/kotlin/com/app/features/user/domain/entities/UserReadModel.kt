package com.app.features.user.domain.entities

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*


@Schema(description = "User Response DTO")
data class UserReadModel(
    val id: Int,
    val email: String,
    val password: String,
    val updatedAt: Date,
    val createdAt: Date,
    val tasks: Collection<Int>,
    val isActive: Boolean,
    val isDeleted: Boolean,
) {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        password = user.password,
        updatedAt = user.updatedAt,
        createdAt = user.createdAt,
        tasks = user.tasks,
        isActive = user.isActive,
        isDeleted = user.isDeleted
    )
}
