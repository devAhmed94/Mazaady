package com.example.mazaady.app.di

import com.example.mazaady.domain.repo.Repo
import com.example.mazaady.domain.usecase.GetUseCase
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
object UseCaseModule {

    @Provides
    fun provideUseCase(repo: Repo): GetUseCase {
        return GetUseCase(repo)
    }
}