package com.example.mazaady.presentation.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.viewpager2.widget.ViewPager2
import com.example.mazaady.R
import com.example.mazaady.databinding.ActivityDetailsBinding
import com.example.mazaady.domain.entities.ItemMazaders
import com.example.mazaady.domain.entities.ItemSameProducts

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val slideAdapter by lazy { SlideAdapter() }
    private val mazadersAdapter by lazy { MazadersAdapter() }
    private val sameProductsAdapter by lazy { SameProductsAdapter() }
    private val mazaders by lazy {
        mutableListOf<ItemMazaders>().apply {
            add(ItemMazaders(R.drawable.car1, "احمد", "45:47:78", "+60"))
            add(ItemMazaders(R.drawable.car3, "احمد", "45:47:78", "+60"))
            add(ItemMazaders(R.drawable.car1, "احمد", "45:47:78", "+60"))
        }
    }

    private val slideImages by lazy {
        mutableListOf<Int>().apply {
            add(R.drawable.car4)
            add(R.drawable.car3)
            add(R.drawable.car1)
            add(R.drawable.car2)

        }
    }
    private val sameProducts by lazy {
        mutableListOf<ItemSameProducts>().apply {
            add(
                ItemSameProducts(
                    R.drawable.car3,
                    "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                    "3 :22:55",
                    "20000"
                )
            )
            add(
                ItemSameProducts(
                    R.drawable.car3,
                    "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                    "3 :22:55",
                    "20000"
                )
            )
            add(
                ItemSameProducts(
                    R.drawable.car3,
                    "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                    "3 :22:55",
                    "20000"
                )
            )
            add(
                ItemSameProducts(
                    R.drawable.car3,
                    "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                    "3 :22:55",
                    "20000"
                )
            )
            add(
                ItemSameProducts(
                    R.drawable.car3,
                    "سيارة سريعة بى ام دابليو تصنيع 2021 حالته جديدة",
                    "3 :22:55",
                    "20000"
                )
            )


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSliderVp()
        setupRvMazaders()
        setupRvSameProducts()

    }

    private fun setupRvSameProducts() {
        binding.rvSameProducts.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity, HORIZONTAL, false)
            setHasFixedSize(false)
            adapter = sameProductsAdapter.apply { fill(sameProducts) }
        }
    }

    private fun setupRvMazaders() {
        binding.rvMazaders.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity, VERTICAL, false)
            setHasFixedSize(false)
            adapter = mazadersAdapter.apply { fill(mazaders) }
        }
    }

    private fun setupSliderVp() {
        with(binding) {
            vpImage.apply {
                adapter = slideAdapter.apply { fill(slideImages) }
                piSlider.count = slideImages.size
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        piSlider.selection = position
                    }
                })
            }

        }

    }
}