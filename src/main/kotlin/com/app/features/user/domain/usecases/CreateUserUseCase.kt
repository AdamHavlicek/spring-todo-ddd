package com.app.features.user.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.app.core.exceptions.InvalidOperationException
import com.app.core.usecases.IUseCase
import com.app.features.user.domain.entities.User
import com.app.features.user.domain.entities.UserCreateModel
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.repositories.IUserRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateUserUseCase(
    val repository: IUserRepository,
) : IUseCase<UserReadModel, UserCreateModel> {

    override fun invoke(params: UserCreateModel): Either<Exception, UserReadModel> {
        val user = User(
            id = -1,
            email = params.email,
            password = params.password,
            createdAt = Date(),
            updatedAt = Date(),
            tasks = arrayListOf(),
        )

        val existingUser = repository.findByEmail(user.email).swap().mapLeft {
            InvalidOperationException("User already exists with specified email")
        }

        val newUser = existingUser.flatMap {
            repository.create(user)
        }

        return newUser.map { UserReadModel(user = it) }
    }

}