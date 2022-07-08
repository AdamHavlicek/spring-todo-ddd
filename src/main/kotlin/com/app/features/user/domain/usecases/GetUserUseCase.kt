package com.app.features.user.domain.usecases

import arrow.core.Either
import com.app.core.usecases.IUseCase
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.services.IUserQueryService
import org.springframework.stereotype.Component

@Component
class GetUserUseCase(
    val service: IUserQueryService,
) : IUseCase<UserReadModel, Int> {
    override fun invoke(params: Int): Either<Exception, UserReadModel> {
        val foundUser = service.findById(params)

        return foundUser
    }

}
