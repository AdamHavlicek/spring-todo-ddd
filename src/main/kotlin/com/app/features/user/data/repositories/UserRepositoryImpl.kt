package com.app.features.user.data.repositories

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.app.core.exceptions.EntityNotFoundException
import com.app.features.user.data.models.User
import com.app.features.user.domain.repositories.IUserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional
import com.app.features.user.domain.entities.User as UserEntity


interface IUserJpaRepository : JpaRepository<User, Int> {
    fun findByEmailIs(email: String): User?
}

@Repository
class UserRepositoryImpl(
    private val jpaRepository: IUserJpaRepository,
) : IUserRepository {

    override fun findAll(): Either<Exception, ArrayList<UserEntity>> {
        val users = jpaRepository.findAll().mapTo(ArrayList()) { it.toDomainEntity() }

        return users.right()
    }

    override fun findById(id: Int): Either<Exception, UserEntity> {
        val foundUser = jpaRepository.findByIdOrNull(id) ?: return EntityNotFoundException("User not found").left()

        return foundUser.toDomainEntity().right()
    }

    override fun findByEmail(email: String): Either<Exception, UserEntity> {
        val foundUser = jpaRepository.findByEmailIs(email) ?: return EntityNotFoundException("User not found").left()

        return foundUser.toDomainEntity().right()
    }

    @Transactional
    override fun create(entity: UserEntity): Either<Exception, UserEntity> {
        val user = User(entity)

        val newUser = jpaRepository.save(user)

        return newUser.toDomainEntity().right()
    }

    @Transactional
    override fun update(entity: UserEntity): Either<Exception, UserEntity> {
        val user = User(entity)
        val updatedUser = jpaRepository.save(user)

        return updatedUser.toDomainEntity().right()
    }

    @Transactional
    override fun deleteById(id: Int): Either<Exception, UserEntity> {
        TODO("Not yet implemented")
    }

}
