package com.app.core.failures

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ValidationFailure(
    override val id: UUID,
    override val message: String
) : BaseFailure()