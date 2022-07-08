package com.app.features.user.data.services

import arrow.core.Either
import com.app.features.user.data.models.User
import com.app.features.user.domain.entities.UserReadModel
import com.app.features.user.domain.services.IUserQueryService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface IUserRepository : JpaRepository<User, Int> {}

@Component
class UserQueryServiceImpl(
    private val userRepository: IUserRepository
) : IUserQueryService {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findById(id: Int): Either<Exception, UserReadModel> {
        TODO("Not yet implemented")
    }


    override fun findAll(): Either<Exception, ArrayList<UserReadModel>> {
        val findAllQuery = entityManager.criteriaBuilder.createQuery(User::class.java)
        val from = findAllQuery.from(User::class.java)
        findAllQuery.select(from)

        val typedQuery = entityManager.createQuery(findAllQuery)

        val users = typedQuery.resultList.mapTo(ArrayList()) { it.toReadModel() }

        return Either.Right(users)
    }


}