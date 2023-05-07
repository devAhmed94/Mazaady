package com.example.mazaady.data.repo

import com.example.mazaady.data.network.ApiServices
import com.example.mazaady.domain.entities.ResCategory
import com.example.mazaady.domain.entities.ResProps
import com.example.mazaady.domain.repo.Repo


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
class CategoriesRepoImpl(private val apiServices: ApiServices) :Repo {
    override suspend fun getCategories(): ResCategory =apiServices.getCategories()
    override suspend fun getProps(cat: Int): ResProps = apiServices.getProps(cat)
    override suspend fun getOptions(id: Int): ResProps =apiServices.getOptions(id)


}