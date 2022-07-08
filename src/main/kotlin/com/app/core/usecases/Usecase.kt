package com.app.core.usecases

import arrow.core.Either

interface IUseCaseNoParams<Type> {
    operator fun invoke(): Either<Exception, Type>
}

interface IUseCase<Type, Params> {
    operator fun invoke(params: Params): Either<Exception, Type>
}
