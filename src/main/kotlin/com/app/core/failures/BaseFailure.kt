package com.app.core.failures

import com.app.core.exceptions.EntityNotFoundException
import com.app.core.exceptions.InvalidOperationException
import java.util.*


interface IBaseFailure {
    val id: UUID
    val message: String
}

abstract class BaseFailure {

    private fun getFactories(id: UUID, message: String): HashMap<Any, () -> IBaseFailure> = hashMapOf(
        InvalidOperationException::class to { BadRequestFailure(id, message) },
        EntityNotFoundException::class to { NotFoundFailures(id, message) }
    )

    fun fromException(exception: Exception): () -> IBaseFailure {
        val message: String = exception.message.orEmpty()

        val factories = getFactories(
            id = UUID.randomUUID(),
            message = message
        )

        return factories[exception::class] ?: throw exception
    }
}

data class BadRequestFailure(
    override val id: UUID,
    override val message: String,
) : BaseFailure(), IBaseFailure

data class NotFoundFailures(
    override val id: UUID,
    override val message: String,
) : BaseFailure(), IBaseFailure