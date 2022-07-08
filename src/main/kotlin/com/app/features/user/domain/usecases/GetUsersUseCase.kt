package com.app.features.user.domain.usecases

import arrow.core.Either
import com.app.core.usecases.IUseCaseNoParams
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.services.IUserQueryService
import org.springframework.stereotype.Component


@Component
class GetUsersUseCase(
    val service: IUserQueryService,
) : IUseCaseNoParams<ArrayList<UserReadModel>> {

    override operator fun invoke(): Either<Exception, ArrayList<UserReadModel>> {
        val users = service.findAll()

        return users
    }
}