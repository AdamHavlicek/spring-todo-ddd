package com.app.features.todo.domain.usecases

import arrow.core.Either
import com.app.core.usecases.IUseCase
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.services.ITodoQueryService
import org.springframework.stereotype.Component

@Component()
class GetTodoUseCase(
    private val service: ITodoQueryService
) : IUseCase<TodoReadModel, Int>{

    override fun invoke(params: Int): Either<Exception, TodoReadModel> {
        return service.findById(params)
    }

}