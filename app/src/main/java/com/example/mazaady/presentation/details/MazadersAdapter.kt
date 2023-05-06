package com.example.mazaady.presentation.details

import com.example.mazaady.app.core.BaseAdapter
import com.example.mazaady.databinding.ItemMazadersListBinding
import com.example.mazaady.domain.entities.ItemMazaders


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 05/05/2023
 */
class MazadersAdapter : BaseAdapter<ItemMazadersListBinding, ItemMazaders>() {
    override fun setContent(binding: ItemMazadersListBinding, item: ItemMazaders, position: Int) {
        with(binding) {
            civProfile.setImageResource(item.image)
            tvName.text = item.name
            tvDate.text = item.date
            tvRate.text = item.rate
        }
    }
}