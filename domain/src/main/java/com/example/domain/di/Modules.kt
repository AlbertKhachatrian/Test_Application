package com.example.domain.di

import com.example.domain.interactor.GetPetrolsInteractor
import com.example.domain.interactor.GetUserInteractor
import com.example.domain.usecase.GetPetrolsUseCase
import com.example.domain.usecase.GetUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetPetrolsInteractor> { GetPetrolsUseCase(get()) }
    factory<GetUserInteractor> { GetUserUseCase(get()) }
}
