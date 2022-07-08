package com.app.features.todo.domain.entities

data class UserUpdateModel(
    val title: String?,
    val ownerId: Int?,
    val isCompleted: Boolean?,
    val isDeleted: Boolean?
)
