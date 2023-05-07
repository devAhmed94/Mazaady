package com.example.mazaady.domain.usecase

import com.example.mazaady.domain.repo.Repo


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
class GetUseCase(private val repo: Repo) {
    suspend fun getCategories() = repo.getCategories()
    suspend fun getProps(cat:Int) = repo.getProps(cat)
    suspend fun getOptions(id:Int) = repo.getOptions(id)

}