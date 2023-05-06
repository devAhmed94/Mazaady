package com.example.mazaady.presentation.category

import com.example.mazaady.app.core.BaseAdapter
import com.example.mazaady.databinding.ItemListRecyclerViewBinding
import com.example.mazaady.domain.entities.ItemListGeneralBottomSheet


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 05/05/2023
 */
class ListAdapter : BaseAdapter<ItemListRecyclerViewBinding, ItemListGeneralBottomSheet>() {
    override fun setContent(
        binding: ItemListRecyclerViewBinding,
        item: ItemListGeneralBottomSheet,
        position: Int
    ) {
        with(binding) {
            tvName.text = item.name
            root.setOnClickListener { onViewClicked(it, item, position) }
        }
    }
}