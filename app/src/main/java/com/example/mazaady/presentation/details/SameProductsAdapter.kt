package com.example.mazaady.presentation.details

import com.example.mazaady.app.core.BaseAdapter
import com.example.mazaady.databinding.ItemSameProductsBinding
import com.example.mazaady.domain.entities.ItemSameProducts


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 09/05/2023
 */
class SameProductsAdapter : BaseAdapter<ItemSameProductsBinding, ItemSameProducts>() {
    override fun setContent(
        binding: ItemSameProductsBinding,
        item: ItemSameProducts,
        position: Int
    ) {
        with(binding) {
            rivImage.setImageResource(item.image)
            tvNameProduct.text = item.name
            tvStartAfterValue.text = item.date
            tvPriceStartMazady.text = item.price
        }
    }
}