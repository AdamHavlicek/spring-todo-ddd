package com.app.features.todo.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.core.exceptions.EntityNotFoundException
import com.app.features.todo.data.models.Todo
import com.app.features.todo.domain.repositories.ITodoRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import jakarta.transaction.Transactional
import com.app.features.todo.domain.entities.Todo as TodoEntity

interface ITodoJpaRepository : JpaRepository<Todo, Int>

@Repository
class TodoRepositoryImpl(
    private val jpaRepository: ITodoJpaRepository,
) : ITodoRepository {

    override fun findAll(): Either<Exception, ArrayList<TodoEntity>> {
        val todos = jpaRepository.findAll().mapTo(ArrayList()) { it.toDomainEntity() }

        return todos.right()
    }

    override fun findById(id: Int): Either<Exception, TodoEntity> {
        val foundTodo = jpaRepository.findByIdOrNull(id) ?: return EntityNotFoundException("Todo not found").left()

        return foundTodo.toDomainEntity().right()
    }

    @Transactional
    override fun create(entity: TodoEntity): Either<Exception, TodoEntity> {
        val todo = Todo(todo = entity)

        val newTodo = jpaRepository.save(todo)

        return newTodo.toDomainEntity().right()
    }

    @Transactional
    override fun update(entity: TodoEntity): Either<Exception, TodoEntity> {
        val todo = Todo(entity)
        val updatedTodo = jpaRepository.save(todo)

        return updatedTodo.toDomainEntity().right()
    }

    override fun deleteById(id: Int): Either<Exception, TodoEntity> {
        TODO("Not yet implemented")
    }

}