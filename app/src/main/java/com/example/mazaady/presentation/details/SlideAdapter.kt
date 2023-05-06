package com.example.mazaady.presentation.details

import com.example.mazaady.app.core.BaseAdapter
import com.example.mazaady.databinding.ItemSlideBinding


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 05/05/2023
 */
class SlideAdapter : BaseAdapter<ItemSlideBinding, Int>() {
    override fun setContent(binding: ItemSlideBinding, item: Int, position: Int) {
        binding.ivSlider.setImageResource(item)

        binding.root.setOnClickListener { onViewClicked(it, item, position) }
    }
}