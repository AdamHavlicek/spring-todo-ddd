package com.app.core.services

import arrow.core.Either

interface IQueryService<T> {
    fun findById(id: Int): Either<Exception, T>
    fun findAll(): Either<Exception, ArrayList<T>>

}