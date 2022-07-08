package com.app.features.todo.domain.usecases

import arrow.core.Either
import com.app.core.usecases.IUseCaseNoParams
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.services.ITodoQueryService
import org.springframework.stereotype.Component

@Component
class GetTodosUseCase(
    private val service: ITodoQueryService
) : IUseCaseNoParams<ArrayList<TodoReadModel>> {
    override fun invoke(): Either<Exception, ArrayList<TodoReadModel>> {
        val todos = service.findAll()

        return todos
    }

}