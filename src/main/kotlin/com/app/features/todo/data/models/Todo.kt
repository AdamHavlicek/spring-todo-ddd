package com.app.features.todo.data.models

import com.app.features.todo.domain.entities.TodoReadModel
import com.app.features.user.data.models.User
import com.app.features.todo.domain.entities.Todo as TodoEntity
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date
import javax.persistence.*

@Entity(name = "Todos")
@DynamicUpdate
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val title: String,
    @Column(
        nullable = false
    )
    val ownerId: Int,
    @Column(
        updatable = false
    )
    val createdAt: Date,
    @LastModifiedDate
    val updatedAt: Date,
    val isCompleted: Boolean,
    val isDeleted: Boolean,
) {
    @ManyToOne
    @JoinColumn(
        name = "ownerId",
        nullable = false,
        insertable = false,
        updatable = false
    )
    lateinit var owner: User

    constructor(todo: TodoEntity) : this(
        id = todo.id,
        title = todo.title,
        ownerId = todo.ownerId,
        createdAt = todo.createdAt,
        updatedAt = todo.updatedAt,
        isCompleted = todo.isCompleted,
        isDeleted = todo.isDeleted
    )

    fun toDomainEntity(): TodoEntity{
        return TodoEntity(
            id = this.id,
            title = this.title,
            ownerId = this.ownerId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isCompleted = this.isCompleted,
            isDeleted = this.isDeleted
        )
    }

    fun toReadModel(): TodoReadModel {
        return TodoReadModel(
            id = this.id,
            title = this.title,
            ownerId = this.ownerId,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isCompleted = this.isCompleted,
            isDeleted = this.isDeleted
        )
    }
}