package com.example.data.datastore

import com.example.base_mvvm.network.Result
import com.example.data.model.PetrolResponse

interface PetrolsRepository {
    suspend fun getPetrols():Result<List<PetrolResponse>>
}