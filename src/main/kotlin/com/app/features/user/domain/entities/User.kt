package com.app.features.user.domain.entities

import arrow.core.Either
import com.app.core.exceptions.InvalidOperationException
import java.util.Date

data class User(
    val id: Int,
    val email: String,
    val password: String,
    val createdAt: Date,
    val updatedAt: Date,
    val tasks: Collection<Int>,
    val isActive: Boolean = true,
    val isDeleted: Boolean = false,
) {

    fun markUserAsDeleted(): Either<InvalidOperationException, User> {
        if (isDeleted) {
            return Either.Left(InvalidOperationException("User Entity is marked as deleted already!"))
        }

        return Either.Right(copy(
            isDeleted = true
        ))
    }

}