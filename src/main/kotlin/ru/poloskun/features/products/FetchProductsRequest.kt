package ru.poloskun.features.products

import kotlinx.serialization.Serializable

@Serializable
data class FetchProductsRequest(
    val token: String
)