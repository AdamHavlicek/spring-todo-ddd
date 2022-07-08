package com.app.features.user.data.services

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.core.exceptions.EntityNotFoundException
import com.app.features.user.data.models.User
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.services.IUserQueryService
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Service
class UserQueryServiceImpl(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val queryFactory:  SpringDataQueryFactory
) : IUserQueryService {

    @Transactional
    override fun findById(id: Int): Either<Exception, UserReadModel> {
        return try {
            val findByIdQuery = queryFactory.singleQuery<User>{
                select(entity(User::class))
                from(entity(User::class))
                where(column(User::id).equal(id))
            }

            findByIdQuery.toReadModel().right()
        } catch (exception: NoResultException) {
            EntityNotFoundException("User not found").left()
        }
    }

    @Transactional
    override fun findAll(): Either<Exception, ArrayList<UserReadModel>> {
        val findAllQuery = entityManager.criteriaBuilder.createQuery(User::class.java)
        val from = findAllQuery.from(User::class.java)
        findAllQuery.select(from)

        val typedQuery = entityManager.createQuery(findAllQuery)

        val users = typedQuery.resultList.map { it.toReadModel() } as ArrayList<UserReadModel>
        return Either.Right(users)
    }


}