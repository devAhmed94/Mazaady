package com.example.mazaady.domain.entities

import com.google.gson.annotations.SerializedName

data class ResCategory(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: Data,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("ads_banners")
        val adsBanners: List<AdsBanner>,
        @SerializedName("categories")
        val categories: List<Category>,
        @SerializedName("google_version")
        val googleVersion: String,
        @SerializedName("huawei_version")
        val huaweiVersion: String,
        @SerializedName("ios_latest_version")
        val iosLatestVersion: String,
        @SerializedName("ios_version")
        val iosVersion: String,
        @SerializedName("statistics")
        val statistics: Statistics
    ) {
        data class AdsBanner(
            @SerializedName("duration")
            val duration: Int,
            @SerializedName("img")
            val img: String,
            @SerializedName("media_type")
            val mediaType: String
        )

        data class Category(
            @SerializedName("children")
            val children: List<Children>,
            @SerializedName("circle_icon")
            val circleIcon: String,
            @SerializedName("description")
            val description: Any,
            @SerializedName("disable_shipping")
            val disableShipping: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("image")
            val image: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("slug")
            val slug: String
        ) {
            data class Children(
                @SerializedName("children")
                val children: Any,
                @SerializedName("circle_icon")
                val circleIcon: String,
                @SerializedName("description")
                val description: Any,
                @SerializedName("disable_shipping")
                val disableShipping: Int,
                @SerializedName("id")
                val id: Int,
                @SerializedName("image")
                val image: String,
                @SerializedName("name")
                val name: String,
                @SerializedName("slug")
                val slug: String
            )
        }

        data class Statistics(
            @SerializedName("auctions")
            val auctions: Int,
            @SerializedName("products")
            val products: Int,
            @SerializedName("users")
            val users: Int
        )
    }
}