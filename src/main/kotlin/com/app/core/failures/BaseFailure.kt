package com.app.core.failures

import com.app.core.exceptions.EntityNotFoundException
import com.app.core.exceptions.InvalidOperationException
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*
import kotlin.reflect.full.findAnnotations


interface IBaseFailure {
    val id: UUID
    val message: String

    fun getAnnotatedStatus(): HttpStatus
}

abstract class BaseFailure : IBaseFailure {

    @JsonIgnore
    override fun getAnnotatedStatus(): HttpStatus {
        val failureStatus = this::class.findAnnotations(ResponseStatus::class).firstOrNull()?.value
        return failureStatus ?: HttpStatus.INTERNAL_SERVER_ERROR
    }

    companion object {
        fun fromException(exception: Exception): () -> IBaseFailure {
            val message: String = exception.message.orEmpty()
            val uuid: UUID = UUID.randomUUID()

            val factories = hashMapOf(
                InvalidOperationException::class to { BadRequestFailure(uuid, message) },
                EntityNotFoundException::class to { NotFoundFailure(uuid, message) },
                MethodArgumentNotValidException::class to {
                    val validationErrors = (exception as MethodArgumentNotValidException).bindingResult.fieldErrors
                    val validationMessage = validationErrors.joinToString(
                        separator = ", "
                    ) { "${it.field}: ${it.defaultMessage}" }
                    ValidationFailure(uuid, validationMessage)
                },
            )

            // throw exception which will be caught and transformed to ServerFailureResponse
            return factories[exception::class] ?: { ServerFailure(id = uuid, message = message) }
        }
    }

}

