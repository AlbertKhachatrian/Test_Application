package com.example.data.repository

import com.example.base_mvvm.network.Result
import com.example.data.R
import com.example.data.datastore.PetrolsRepository
import com.example.data.model.PetrolResponse

class PetrolsRepositoryImpl: PetrolsRepository {
    val petrols = listOf(
        PetrolResponse(
            name = "Regular",
            price = 480,
            image = R.drawable.ic_regulyar
        ),
        PetrolResponse(
            name = "Premium",
            price = 520,
            image = R.drawable.ic_premum
        ),
        PetrolResponse(
            name = "Diesel",
            price = 520,
            image = R.drawable.ic_diesel
        ),
        PetrolResponse(
            name = "Super",
            price = 520,
            image = R.drawable.ic_super
        )
    )
    override suspend fun getPetrols(): Result<List<PetrolResponse>> = Result.Success(petrols)

}