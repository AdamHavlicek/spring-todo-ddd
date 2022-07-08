package com.app.features.todo.domain.entities

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.core.exceptions.InvalidOperationException
import java.util.Date

data class Todo(
    val id: Int,
    val title: String,
    val ownerId: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false
) {

    fun markTodoAsDeleted(): Either<InvalidOperationException, Todo> {
        if (isDeleted) {
            return InvalidOperationException("Todo Entity is marked as deleted already!").left()
        }

        return copy(
            isDeleted = true
        ).right()
    }
}
