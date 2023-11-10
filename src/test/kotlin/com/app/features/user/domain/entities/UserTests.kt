package com.app.features.user.domain.entities

import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import java.util.Date

internal class UserTests : ShouldSpec({
    context("MarkUserAsDeleted") {
        should("return right side when [User.isDeleted] is false") {
            // Arrange
            val testedEntity = User(
                id = 0,
                createdAt = Date(0),
                updatedAt = Date(0),
                email = "test@test.com",
                isDeleted = false,
                isActive = true,
                password = "test",
                tasks = listOf(0)
            )

            // Act
            val result = testedEntity.markUserAsDeleted()

            // Assert
            result.shouldBeRight()
            result.onRight { user: User -> user.isDeleted.shouldBeTrue() }
        }
    }
})