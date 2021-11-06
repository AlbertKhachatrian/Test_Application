package com.example.domain.mapper

import com.example.data.model.UserResponse
import com.example.domain.model.User

object UserMapper {
    fun UserResponse.map(): User=
        User(
            paymentBalance = this.paymentBalance,
            accumulativeBalance = this.accumulativeBalance,
            name = this.name,
            email = this.email
        )
}