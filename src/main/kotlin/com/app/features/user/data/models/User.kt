package com.app.features.user.data.models

import com.app.features.todo.data.models.Todo
import com.app.features.user.domain.entities.UserReadModel
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.persistence.*
import com.app.features.user.domain.entities.User as UserEntity

@Entity(name = "Users")
@DynamicUpdate
class User(
    @Id
    @GeneratedValue
    val id: Int,
    val email: String,
    val password: String,
    @Column(
        updatable = false
    )
    val createdAt: Date,
    @LastModifiedDate
    val updatedAt: Date,
    val isActive: Boolean,
    val isDeleted: Boolean,
) {

    @OneToMany(
        mappedBy = "owner",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true,
    )
    val tasks: List<Todo> = emptyList()

    constructor(user: UserEntity) : this(
        user.id,
        user.email,
        user.password,
        user.createdAt,
        user.updatedAt,
        user.isActive,
        user.isDeleted
    )

    fun toDomainEntity(): UserEntity {
        return UserEntity(
            id = id,
            email = email,
            password = password,
            createdAt = createdAt,
            updatedAt = updatedAt,
            tasks = tasks.map { it.id },
            isActive = isActive,
            isDeleted = isDeleted
        )
    }

    fun toReadModel(): UserReadModel {
        return UserReadModel(
            id = id,
            email = email,
            password = password,
            createdAt = createdAt,
            updatedAt = updatedAt,
            tasks = tasks.map { it.id },
            isActive = isActive,
            isDeleted = isDeleted
        )
    }
}
