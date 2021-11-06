package com.example.domain.model

import androidx.annotation.DrawableRes

data class Petrol(
    val name:String?,
    var price: Int?,
    @DrawableRes
    val image: Int?,
    var isSelected: Boolean
)