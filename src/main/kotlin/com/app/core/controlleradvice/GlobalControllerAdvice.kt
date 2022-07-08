package com.app.core.controlleradvice

import com.app.core.failures.BaseFailure
import com.app.core.failures.IBaseFailure
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    private fun toFailure(exception: Throwable): IBaseFailure {
        return BaseFailure.fromException(exception as Exception)()
    }

    @ExceptionHandler(Throwable::class)
    private fun handleException(exception: Throwable): ResponseEntity<IBaseFailure> {
        val failure = toFailure(exception)

        return ResponseEntity.status(failure.getAnnotatedStatus()).body(failure)
    }
}
