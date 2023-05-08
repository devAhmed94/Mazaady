package com.example.mazaady.domain.entities


import com.google.gson.annotations.SerializedName

data class ResOption(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("description")
        val description: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("list")
        val list: Boolean,
        @SerializedName("name")
        val name: String,
        @SerializedName("options")
        val options: List<Option>,
        @SerializedName("other_value")
        val otherValue: Any,
        @SerializedName("parent")
        val parent: Int,
        @SerializedName("slug")
        val slug: String,
        @SerializedName("type")
        val type: Any,
        @SerializedName("value")
        val value: String
    ) {
        data class Option(
            @SerializedName("child")
            val child: Boolean,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("parent")
            val parent: Int,
            @SerializedName("slug")
            val slug: String
        )
    }
}