package com.example.domain.usecase


import com.example.base_mvvm.network.CallException
import com.example.base_mvvm.network.Result
import com.example.data.datastore.PetrolsRepository
import com.example.domain.interactor.GetPetrolsInteractor
import com.example.domain.mapper.PetrolMapper.map
import com.example.domain.model.Petrol

class GetPetrolsUseCase(
    private val petrolsRepository: PetrolsRepository
):GetPetrolsInteractor {
    override suspend fun invoke(): Result<List<Petrol>> {
        return when(val data = petrolsRepository.getPetrols()) {
            is Result.Success -> {
                Result.Success(data.data.map())
            }
            is Result.Error -> {
                Result.Error(CallException(errorMessage = data.errors.errorMessage, errorCode = data.errors.errorCode))
            }
        }
    }
}