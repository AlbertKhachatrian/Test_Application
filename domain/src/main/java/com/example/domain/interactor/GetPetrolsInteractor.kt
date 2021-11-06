package com.example.domain.interactor

import com.example.base_mvvm.network.Result
import com.example.domain.model.Petrol

interface GetPetrolsInteractor {
    suspend operator fun invoke(): Result<List<Petrol>>
}