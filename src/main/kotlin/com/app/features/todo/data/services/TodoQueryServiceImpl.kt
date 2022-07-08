package com.app.features.todo.data.services

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.core.exceptions.EntityNotFoundException
import com.app.features.todo.data.models.Todo
import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.todo.domain.services.ITodoQueryService
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Service
class TodoQueryServiceImpl(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val queryFactory: SpringDataQueryFactory
) : ITodoQueryService {

    @Transactional
    override fun findById(id: Int): Either<Exception, TodoReadModel> {
        return try {
            val findByIdQuery = queryFactory.singleQuery<Todo> {
                select(entity(Todo::class))
                from(entity(Todo::class))
                where(column(Todo::id).equal(id))
            }

            findByIdQuery.toReadModel().right()
        } catch (exception: NoResultException) {
            EntityNotFoundException("Todo not found").left()
        }
    }

    @Transactional
    override fun findAll(): Either<Exception, ArrayList<TodoReadModel>> {
        val findAllQuery = queryFactory.listQuery<Todo> {
            select(entity(Todo::class))
            from(entity(Todo::class))
        }

        return findAllQuery.mapTo(ArrayList()) { it.toReadModel() }.right()
    }
}