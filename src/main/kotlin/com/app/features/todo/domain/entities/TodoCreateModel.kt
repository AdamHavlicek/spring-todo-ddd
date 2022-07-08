package com.app.features.todo.domain.entities

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty


data class TodoCreateModel(
    @field:NotEmpty()
    val title: String,
    @field:Min(1)
    val ownerId: Int,
)
