package com.app.features.user.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.app.core.usecases.IUseCase
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.entities.UserUpdateModel
import com.app.features.user.domain.repositories.IUserRepository
import org.springframework.stereotype.Component

typealias UpdateUserData = Pair<Int, UserUpdateModel>

@Component
class UpdateUserUseCase(
    val repository: IUserRepository,
) : IUseCase<UserReadModel, UpdateUserData> {

    override fun invoke(params: UpdateUserData): Either<Exception, UserReadModel> {
        val (userId, data) = params

        val user = repository.findById(userId)
        val updatedUser = user.map {
            it.copy(
                email = data.email ?: it.email,
                password = data.password ?: it.password,
                isDeleted = data.isDeleted ?: it.isDeleted,
                isActive = data.isActive ?: it.isActive,
            )
        }.flatMap {
            repository.update(it)
        }.map {
            UserReadModel(it)
        }

        return updatedUser
    }

}