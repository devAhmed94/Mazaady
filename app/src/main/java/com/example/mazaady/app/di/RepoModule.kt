package com.example.mazaady.app.di

import com.example.mazaady.data.network.ApiServices
import com.example.mazaady.data.repo.CategoriesRepoImpl
import com.example.mazaady.domain.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiServices: ApiServices): Repo {
        return CategoriesRepoImpl(apiServices)
    }
}