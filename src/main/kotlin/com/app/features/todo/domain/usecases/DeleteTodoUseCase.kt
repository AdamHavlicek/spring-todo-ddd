package com.app.features.todo.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.app.core.usecases.IUseCase
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.repositories.ITodoRepository
import org.springframework.stereotype.Component

@Component
class DeleteTodoUseCase(
    private val repository: ITodoRepository
) : IUseCase<TodoReadModel, Int> {
    override fun invoke(params: Int): Either<Exception, TodoReadModel> {

        val todo = repository.findById(params)

        val updatedTodo = todo.flatMap {
            it.markTodoAsDeleted()
        }.flatMap {
            this.repository.update(it)
        }.map {
            TodoReadModel(it)
        }

        return updatedTodo
    }
}