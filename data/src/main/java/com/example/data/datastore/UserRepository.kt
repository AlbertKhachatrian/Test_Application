package com.example.data.datastore

import com.example.base_mvvm.network.Result
import com.example.data.model.UserResponse

interface UserRepository {
    suspend fun getUser():Result<UserResponse>
}