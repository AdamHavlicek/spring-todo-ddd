package com.app.core.repositories

import arrow.core.Either

interface IRepository<Entity> {
    fun findAll(): Either<Exception, ArrayList<Entity>>
    fun findById(id: Int): Either<Exception, Entity>
    fun create(entity: Entity): Either<Exception, Entity>
    fun update(entity: Entity): Either<Exception, Entity>
    fun deleteById(id: Int): Either<Exception, Entity>
}