package com.example.mazaady.presentation.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazaady.domain.entities.ItemListGeneralBottomSheet
import com.example.mazaady.domain.entities.ResCategory
import com.example.mazaady.domain.entities.ResOption
import com.example.mazaady.domain.entities.ResProps
import com.example.mazaady.domain.usecase.GetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getUseCase: GetUseCase
) : ViewModel() {

    private val _categories: MutableStateFlow<ResCategory?> = MutableStateFlow(null)
    val categories: StateFlow<ResCategory?> = _categories

    private val _props: MutableStateFlow<ResProps?> = MutableStateFlow(null)
    val props: StateFlow<ResProps?> = _props

    private val _options: MutableStateFlow<ResOption?> = MutableStateFlow(null)
    val options: StateFlow<ResOption?> = _options

    val setItemSelectedLiveDate = MutableLiveData<ItemListGeneralBottomSheet>()

    fun getCategories() {
        viewModelScope.launch {
            try {
                _categories.value = getUseCase.getCategories()
            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
            }
        }
    }

    fun getProps(cat: Int) {
        viewModelScope.launch {
            try {
                _props.value = getUseCase.getProps(cat)
            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
            }
        }
    }

    fun getOptions(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("ZZzZZ", "getOptions: live data")
                _options.value = null
                _options.value = getUseCase.getOptions(id)

            } catch (e: Exception) {
                Log.d("TAG", e.message.toString())
            }
        }
    }

}