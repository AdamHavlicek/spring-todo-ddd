package com.app.core.exceptions

class EntityNotFoundException(
    message: String = "Entity Not Found"
) : Exception(message)