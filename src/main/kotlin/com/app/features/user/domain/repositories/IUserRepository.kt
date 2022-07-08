package com.app.features.user.domain.repositories

import arrow.core.Either
import com.app.core.repositories.IRepository
import com.app.features.user.domain.entities.User

interface IUserRepository : IRepository<User> {

    fun findByEmail(email: String): Either<Exception, User>
}

