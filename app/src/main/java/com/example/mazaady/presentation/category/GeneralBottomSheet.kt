package com.example.mazaady.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaady.databinding.BottomSheetBinding
import com.example.mazaady.domain.entities.ItemListGeneralBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 04/05/2023
 */
class GeneralBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetBinding
    private var itemlist = mutableListOf<ItemListGeneralBottomSheet>()
    private val listAdapter by lazy { ListAdapter() }
    private var title = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        binding.clContainer.maxHeight = (resources.displayMetrics.heightPixels * 0.95).toInt()
        binding.clContainer.minHeight = (resources.displayMetrics.heightPixels * 0.95).toInt()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingRecycleView()
        settingContent()
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED

    }

    private fun settingRecycleView() {
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = listAdapter.apply { fill(itemlist) }
        }

        listAdapter.setOnClickListener { _, item, _ ->
            dismiss()
            (requireActivity() as CategoryActivity).viewModel.setItemSelectedLiveDate.value = item

        }

    }

    private fun settingContent() {
        binding.tvTitle.text = title
    }

    private fun setListeners() {
        with(binding) {
            ivClose.setOnClickListener { dismiss() }

            etSearch.doAfterTextChanged { text ->
                val filterList = itemlist.filter { it.name.contains(text.toString(), true) }
                when {
                    filterList.isEmpty() -> listAdapter.fill(filterList)
                    text.isNullOrEmpty() -> {
                        listAdapter.fill(itemlist)
                    }
                    else -> {

                        listAdapter.fill(filterList)
                    }
                }

            }

        }
    }


    fun setListCategories(_list: MutableList<ItemListGeneralBottomSheet>, _title: String) {
        itemlist = _list
        title = _title
    }


}