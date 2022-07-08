package com.app.features.todo.domain.usecases

import arrow.core.Either
import com.app.core.usecases.IUseCase
import com.app.features.todo.domain.entities.Todo
import com.app.features.todo.domain.entities.TodoCreateModel
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.repositories.ITodoRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreateTodoUseCase(
    private val repository: ITodoRepository,
) : IUseCase<TodoReadModel, TodoCreateModel> {

    override fun invoke(params: TodoCreateModel): Either<Exception, TodoReadModel> {
        val todo = Todo(
            id = -1,
            title = params.title,
            ownerId = params.ownerId,
            createdAt = Date(),
            updatedAt = Date(),
        )

        val newTodo = repository.create(todo)

        return newTodo.map { TodoReadModel(todo = it) }
    }

}