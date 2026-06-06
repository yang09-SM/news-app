package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("pic") val pic: String,
    @SerializedName("points") val points: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("category") val category: String
)
