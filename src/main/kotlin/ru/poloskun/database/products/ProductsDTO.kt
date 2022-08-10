package ru.poloskun.database.products

import kotlinx.serialization.Serializable

@Serializable
class ProductsDTO(
    val id: Int,
    var article: String,
    val name: String,
    val quantity: Int,
    val purchaseCost: Double,
    val retailPrice: Double,
    val isBottling: Boolean,
)