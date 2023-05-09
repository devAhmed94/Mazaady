package com.example.mazaady.presentation.category


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.mazaady.R
import com.example.mazaady.app.utils.OTHER_ID
import com.example.mazaady.databinding.ActivityCategoryBinding
import com.example.mazaady.domain.entities.ItemListGeneralBottomSheet
import com.example.mazaady.domain.entities.ResCategory
import com.example.mazaady.domain.entities.ResOption
import com.example.mazaady.domain.entities.ResProps
import com.example.mazaady.presentation.details.DetailsActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    val viewModel: CategoryViewModel by viewModels()
    private lateinit var binding: ActivityCategoryBinding
    private val bottomSheet by lazy { GeneralBottomSheet() }
    private lateinit var categoriesList: MutableList<ResCategory.Data.Category>
    private lateinit var subCategoryList: MutableList<ResCategory.Data.Category.Children>
    private lateinit var propsList: MutableList<ResProps.Data>


    private val itemBottomSheetList by lazy { mutableListOf<ItemListGeneralBottomSheet>() }
    private lateinit var itemClickedBinding: TextInputEditText
    private var itemClickedId = -1
    private var subCategorySelectedId = -1


    private var optionClickedName = ""
    private var optionSelectedIdWithoutOther = -1
    private var counterOfOther = 0
    private var counterOfLastOption = 0

    private val listEditMap by lazy { HashMap<String, TextInputEditText>() }
    private val listEditMapOther by lazy { HashMap<String, TextInputLayout>() }
    private val ids by lazy { mutableListOf<Int>() }

    private val idsMapOption by lazy { HashMap<String, Int>() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCategories()
        settingListeners()
        observeItemSelected()
    }

    private fun observeItemSelected() {
        viewModel.setItemSelectedLiveDate.observe(this) {
            itemClickedBinding.setText(it.name)
            if (itemClickedBinding == binding.etMainCategory) {
                itemClickedId = it.id
                with(binding) {
                    llDynamicEditText.removeAllViews()
                    etSubCategory.setText("")
                    subCategorySelectedId = -1
                    binding.btnSubmit.isVisible = false
                }

            } else if (itemClickedBinding == binding.etSubCategory) {
                subCategorySelectedId = it.id
                counterOfOther = 0
                counterOfLastOption = 0
                getProps()

            } else {

                if (listEditMapOther.keys.contains("other_${optionClickedName}")) {
                    binding.llDynamicEditText.removeView(listEditMapOther["other_$optionClickedName"])
                    if (counterOfOther > 0)
                        counterOfOther--
                }

                if (it.id == OTHER_ID) {
                    counterOfOther++
                    drawOther(optionSelectedIdWithoutOther)

                } else if (it.child != null && it.child == true) {

                    settingOptions(it.id)
                }

            }

        }
    }

    private fun settingOptions(id: Int) {
        viewModel.getOptions(id)
        lifecycleScope.launch {
            viewModel.options.collect {
                it?.data?.let { opt ->
                    drawOptions(opt as MutableList<ResOption.Data>)
                }
            }
        }
    }


    private fun getProps() {
        viewModel.getProps(subCategorySelectedId)
        lifecycleScope.launch {
            viewModel.props.collect {
                it?.data?.let { data ->
                    propsList = data as MutableList<ResProps.Data>
                    if (::propsList.isInitialized) {
                        binding.llDynamicEditText.removeAllViews()
                        listEditMap.clear()
                        ids.clear()
                        generateEditTextDynamic(propsList)
                        binding.btnSubmit.isVisible = true
                    }
                }
            }
        }
    }

    private fun getCategories() {
        viewModel.getCategories()
        lifecycleScope.launch {
            viewModel.categories.collect {
                it?.data?.let { data ->
                    categoriesList = data.categories as MutableList<ResCategory.Data.Category>
                }

            }
        }
    }

    private fun settingListeners() {
        with(binding) {
            etMainCategory.setOnClickListener {
                itemClickedBinding = etMainCategory
                settingMainCategories()

            }
            etSubCategory.setOnClickListener {
                itemClickedBinding = etSubCategory
                if (itemClickedId != -1) settingSubCategory()
            }

            llToolbar.ivNext.setOnClickListener {
                startActivity(Intent(this@CategoryActivity, DetailsActivity::class.java))
            }

            btnSubmit.setOnClickListener {

                listEditMap.forEach { item ->
                    Toast.makeText(
                        this@CategoryActivity,
                        "${item.key} => ${item.value.text}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
    }

    private fun settingSubCategory() {
        bottomSheet.apply {
            itemBottomSheetList.clear()
            idsMapOption.clear()

            categoriesList.forEach { item ->
                if (item.id == itemClickedId) {
                    subCategoryList =
                        item.children as MutableList<ResCategory.Data.Category.Children>
                }
            }
            if (::subCategoryList.isInitialized) subCategoryList.forEach { item ->
                itemBottomSheetList.add(ItemListGeneralBottomSheet(item.name, item.id))
            }
            setListCategories(itemBottomSheetList, "Sub Category")
            show(supportFragmentManager, "categories_sheet")

        }

    }

    private fun settingMainCategories() {
        bottomSheet.apply {
            itemBottomSheetList.clear()

            if (::categoriesList.isInitialized) categoriesList.forEach { item ->
                itemBottomSheetList.add(ItemListGeneralBottomSheet(item.name, item.id))
            }
            setListCategories(itemBottomSheetList, "Main Category")
            show(supportFragmentManager, "categories_sheet")
        }

    }

    private fun generateEditTextDynamic(props: MutableList<ResProps.Data>) {

        props.forEachIndexed { _, item ->

            val til = TextInputLayout(this)
            til.hint = item.name
            til.boxBackgroundColor = ContextCompat.getColor(this, R.color.white)
            til.boxStrokeColor = ContextCompat.getColor(this, R.color.silverChalice)
            til.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE

            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            params.setMargins(15, 25, 15, 25)
            til.layoutParams = params

            val et = TextInputEditText(til.context)
            val etParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 150
            )
            et.layoutParams = etParams
            et.textSize = 14.0f

            if (item.options.isNotEmpty()) {
                et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                et.isFocusable = false
            }



            et.setOnClickListener {


                if (item.options.isNotEmpty()) {
                    optionClickedName = item.name
                    itemClickedBinding = et
                    optionSelectedIdWithoutOther = item.id
                    itemBottomSheetList.clear()
                    itemBottomSheetList.add(
                        ItemListGeneralBottomSheet(
                            getString(R.string.other), OTHER_ID
                        )
                    )
                    item.options.forEach { item ->
                        itemBottomSheetList.add(
                            ItemListGeneralBottomSheet(
                                item.name, item.id, item.child
                            )
                        )
                    }
                    bottomSheet.setListCategories(itemBottomSheetList, item.name)
                    bottomSheet.show(supportFragmentManager, "categories_sheet")

                }
            }


            listEditMap[item.name] = et
            til.addView(et)
            binding.llDynamicEditText.addView(til)

        }


    }

    private fun drawOther(oldOptionSelectedId: Int) {
        var pos = 0
        var name = ""
        propsList.forEachIndexed { index, item ->
            if (item.id == oldOptionSelectedId) {
                pos = index
                name = item.name
            }
        }

        val tilOther = TextInputLayout(this)
        tilOther.hint = getString(R.string.specify)
        tilOther.boxBackgroundColor = ContextCompat.getColor(this, R.color.white)
        tilOther.boxStrokeColor = ContextCompat.getColor(this, R.color.silverChalice)
        tilOther.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE

        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(15, 25, 15, 25)
        tilOther.layoutParams = params

        val etOther = TextInputEditText(tilOther.context)
        val etParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 150
        )
        etOther.layoutParams = etParams
        etOther.textSize = 14.0f
        listEditMap["other_$name"] = etOther
        tilOther.addView(etOther)
        listEditMapOther["other_$name"] = tilOther
        Log.d("ZZzZZ", "drawOther:$pos + $counterOfOther + $counterOfLastOption +   ")
        binding.llDynamicEditText.addView(tilOther, pos + counterOfOther + counterOfLastOption)

    }

    private fun drawOptions(options: MutableList<ResOption.Data>) {
        var pos = 0
        propsList.forEachIndexed { index, item ->
            if (item.id == optionSelectedIdWithoutOther) {
                pos = index
            }
        }

        options.forEachIndexed { index, item ->
            val tilOther = TextInputLayout(this)
            tilOther.hint = item.name
            tilOther.boxBackgroundColor = ContextCompat.getColor(this, R.color.white)
            tilOther.boxStrokeColor = ContextCompat.getColor(this, R.color.silverChalice)
            tilOther.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE

            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            params.setMargins(15, 25, 15, 25)
            tilOther.layoutParams = params

            val etOther = TextInputEditText(tilOther.context)
            val etParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 150
            )
            etOther.layoutParams = etParams
            etOther.textSize = 14.0f

            if (item.options.isNotEmpty()) {
                etOther.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
                etOther.isFocusable = false
            }

            etOther.setOnClickListener {
                if (item.options.isNotEmpty()) {
                    optionClickedName = item.name
                    itemClickedBinding = etOther
                    itemBottomSheetList.clear()
                    itemBottomSheetList.add(
                        ItemListGeneralBottomSheet(
                            getString(R.string.other), OTHER_ID
                        )
                    )
                    item.options.forEach { item ->
                        itemBottomSheetList.add(
                            ItemListGeneralBottomSheet(
                                item.name, item.id, item.child
                            )
                        )
                    }
                    bottomSheet.setListCategories(itemBottomSheetList, item.name)
                    bottomSheet.show(supportFragmentManager, "categories_sheet")

                }
            }



            tilOther.addView(etOther)
            if (!(idsMapOption.keys.contains(item.name) && idsMapOption.values.contains(item.id))) {
                listEditMap[item.name] = etOther
                idsMapOption.set(item.name, item.id)
                Log.d(
                    "ZZzZZ",
                    "drawOptions:$pos + $counterOfOther + $counterOfLastOption + $index  "
                )
                binding.llDynamicEditText.addView(
                    tilOther, pos + counterOfOther + counterOfLastOption + index + 1
                )
                counterOfLastOption++
            }


        }
    }


}