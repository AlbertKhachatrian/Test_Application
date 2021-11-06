package com.example.data.di

import com.example.data.datastore.PetrolsRepository
import com.example.data.datastore.UserRepository
import com.example.data.repository.PetrolsRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repoModule = module {
    single<PetrolsRepository> { PetrolsRepositoryImpl() }
    single<UserRepository> { UserRepositoryImpl() }
}