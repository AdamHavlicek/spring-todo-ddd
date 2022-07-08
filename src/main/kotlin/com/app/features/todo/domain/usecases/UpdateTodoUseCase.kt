package com.app.features.todo.domain.usecases

import arrow.core.Either
import arrow.core.flatMap
import com.app.core.usecases.IUseCase
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.entities.TodoUpdateModel
import com.app.features.todo.domain.repositories.ITodoRepository
import org.springframework.stereotype.Component

typealias UpdateTodoData = Pair<Int, TodoUpdateModel>

@Component
class UpdateTodoUseCase(
    private val repository: ITodoRepository
) : IUseCase<TodoReadModel, UpdateTodoData> {
    override fun invoke(params: UpdateTodoData): Either<Exception, TodoReadModel> {
        val (todoId, data) = params

        val todo = repository.findById(todoId)
        val updatedTodo = todo.map {
            it.copy(
                title = data.title ?: it.title,
                ownerId = data.ownerId ?: it.ownerId,
                isCompleted = data.isCompleted ?: it.isCompleted,
                isDeleted = data.isDeleted ?: it.isDeleted
            )
        }.flatMap {
            repository.update(it)
        }.map {
            TodoReadModel(it)
        }

        return updatedTodo
    }
}