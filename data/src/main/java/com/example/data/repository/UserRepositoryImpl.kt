package com.example.data.repository

import com.example.base_mvvm.network.Result
import com.example.data.datastore.UserRepository
import com.example.data.model.UserResponse

class UserRepositoryImpl: UserRepository {
    val user = UserResponse(
        name = "Marc Fernandez",
        email = "marcfernandez@gmail.com",
        accumulativeBalance = 12000,
        paymentBalance = 7000
    )
    override suspend fun getUser(): Result<UserResponse> {
        //do an api call
        return Result.Success(user)
    }
}