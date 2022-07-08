package com.app.features.todo.domain.entities

import java.util.*

data class TodoReadModel(
    val id: Int,
    val title: String,
    val ownerId: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val isCompleted: Boolean,
    val isDeleted: Boolean,
) {
    constructor(todo: Todo) : this(
        id = todo.id,
        title = todo.title,
        ownerId = todo.ownerId,
        createdAt = todo.createdAt,
        updatedAt = todo.updatedAt,
        isCompleted = todo.isCompleted,
        isDeleted = todo.isDeleted
    )
}
