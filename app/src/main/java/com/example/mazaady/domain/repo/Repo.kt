package com.example.mazaady.domain.repo

import com.example.mazaady.domain.entities.ResCategory
import com.example.mazaady.domain.entities.ResOption
import com.example.mazaady.domain.entities.ResProps


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
interface Repo {
    suspend fun getCategories():ResCategory
    suspend fun getProps(cat:Int):ResProps
    suspend fun getOptions(id:Int):ResOption
}