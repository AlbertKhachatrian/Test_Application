package com.example.domain.interactor

import com.example.base_mvvm.network.Result
import com.example.domain.model.User

interface GetUserInteractor {
    suspend operator fun invoke():Result<User>
}