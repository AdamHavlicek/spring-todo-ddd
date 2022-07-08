package com.app.features.user.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.app.core.usecases.IUseCase
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.repositories.IUserRepository
import org.springframework.stereotype.Component

@Component
class DeleteUserUseCase(
    private val repository: IUserRepository,
) : IUseCase<UserReadModel, Int> {

    override fun invoke(params: Int): Either<Exception, UserReadModel> {

        val user = repository.findById(params)

        val updatedUser = user.flatMap {
            it.markUserAsDeleted()
        }.flatMap {
            this.repository.update(it)
        }.map {
            UserReadModel(it)
        }

        return updatedUser
    }
}