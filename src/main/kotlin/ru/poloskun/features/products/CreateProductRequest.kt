package ru.poloskun.features.products

import kotlinx.serialization.Serializable
import ru.poloskun.database.products.Products
import ru.poloskun.database.products.ProductsDTO

@Serializable
data class CreateProductRequest(
    val id: Int,
    var article: String,
    val name: String,
    val quantity: Int,
    val purchaseCost: Double,
    val retailPrice: Double,
    val isBottling: Boolean,
)

fun CreateProductRequest.mapToProductsDTO(): ProductsDTO =
    ProductsDTO(
        id = this.id,
        article = this.article,
        name = this.name,
        quantity = this.quantity,
        purchaseCost = this.purchaseCost,
        retailPrice = this.retailPrice,
        isBottling = this.isBottling,
    )