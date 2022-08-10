package ru.poloskun.features.products

import kotlinx.serialization.Serializable


@Serializable
data class CreateProductRequest(
    var article: String,
    val name: String,
    val quantity: Int,
    val purchaseCost: Double,
    val retailPrice: Double,
    val isBottling: Boolean,
)