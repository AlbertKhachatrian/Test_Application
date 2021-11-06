package com.example.base_mvvm.network

data class CallException<ErrorBody>(
    val errorCode: Int? = null,
    val errorMessage: String? = null
)
