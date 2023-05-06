package com.example.mazaady.app.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Ahmed Ali Ebaid
 * ahmedali26002844@gmail.com
 * 07/09/2022
 */
class EndlessScrollListener(private val layoutManager: LayoutManager) : RecyclerView.OnScrollListener() {

  private var visibleThreshold = 5
  private var lastVisibleItem = 0
  private var totalItemCount = 0
  private var page = 1
  private var isLoading = false
  private var loadMoreListener: ((page: Int) -> Unit)? = null

  init {
    when (layoutManager) {
      is GridLayoutManager -> visibleThreshold *= layoutManager.spanCount
      is StaggeredGridLayoutManager -> visibleThreshold *= layoutManager.spanCount
    }
  }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)

    if (dy <= 0) return

    totalItemCount = layoutManager.itemCount

    when (layoutManager) {
      is StaggeredGridLayoutManager ->
        lastVisibleItem = getLastVisibleItem(layoutManager.findLastVisibleItemPositions(null))
      is GridLayoutManager -> lastVisibleItem = layoutManager.findLastVisibleItemPosition()
      is LinearLayoutManager -> lastVisibleItem = layoutManager.findLastVisibleItemPosition()
    }

    if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
      loadMoreListener?.invoke(++page)
      isLoading = true
    }
  }

  fun getPage() = page

  fun resetPage() {
    page = 1
  }

  fun isLoading() = isLoading

  fun setLoaded() {
    isLoading = false
  }

  fun setOnLoadMoreListener(loadMoreListener: (page: Int) -> Unit): EndlessScrollListener {
    this.loadMoreListener = loadMoreListener
    return this
  }

  private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
    var maxSize = 0
    for (i in lastVisibleItemPositions.indices) {
      if (i == 0) maxSize = lastVisibleItemPositions[i]
      else if (lastVisibleItemPositions[i] > maxSize) maxSize = lastVisibleItemPositions[i]
    }
    return maxSize
  }
}