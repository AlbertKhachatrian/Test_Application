package com.example.domain.mapper

import com.example.data.model.PetrolResponse
import com.example.domain.model.Petrol

object PetrolMapper {

    fun List<PetrolResponse>.map(): List<Petrol>{
        val list = mutableListOf<Petrol>()
        this.forEach {
            list.add(
                Petrol(
                    name = it.name,
                    image = it.image,
                    price = it.price,
                    isSelected = false
                )
            )
        }
        return list
    }
}