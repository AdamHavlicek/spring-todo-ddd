package com.app.features.todo.domain.entities

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty


data class TodoCreateModel(
    @field:NotEmpty()
    val title: String,
    @field:Min(1)
    val ownerId: Int,
)
