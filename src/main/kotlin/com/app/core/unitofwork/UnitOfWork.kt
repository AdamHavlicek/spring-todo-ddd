package com.app.core.unitofwork

interface IUnitOfWork<out T> {

    fun begin(): Unit
    fun commit(): Unit
    fun rollback(): Unit
}
