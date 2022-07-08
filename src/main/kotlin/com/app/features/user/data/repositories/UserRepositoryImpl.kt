package com.app.features.user.data.repositories

import arrow.core.Either
//import com.app.features.user.data.models.User
import com.app.features.user.domain.entities.User
import com.app.features.user.domain.repositories.IUserRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class UserRepository(
    @PersistenceContext
    private val entityManager: EntityManager
) : IUserRepository {

    override fun findAll(): Either<Exception, ArrayList<User>> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): Either<Exception, User> {
        TODO("Not yet implemented")
    }

    override fun create(entity: User): Either<Exception, User> {
        TODO("Not yet implemented")
    }

    override fun update(entity: User): Either<Exception, User> {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Int): Either<Exception, User> {
        TODO("Not yet implemented")
    }

}
