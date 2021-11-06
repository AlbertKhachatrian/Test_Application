package com.example.domain.usecase

import com.example.base_mvvm.network.CallException
import com.example.base_mvvm.network.Result
import com.example.data.datastore.UserRepository
import com.example.domain.interactor.GetUserInteractor
import com.example.domain.mapper.UserMapper.map
import com.example.domain.model.User

class GetUserUseCase(
    private val userRepository: UserRepository
): GetUserInteractor {
    override suspend fun invoke(): Result<User> {
        return when(val data = userRepository.getUser()){
            is Result.Success -> {
                Result.Success(data.data.map())
            }
            is Result.Error -> {
                Result.Error(CallException(errorCode = data.errors.errorCode, errorMessage = data.errors.errorMessage))
            }
        }
    }
}