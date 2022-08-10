package ru.poloskun.features.products

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.poloskun.database.products.Products
import ru.poloskun.utils.TokenValidator

class ProductsController(private val call: ApplicationCall) {

    suspend fun fetchAllProducts() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenValidator.isTokenValid(token.orEmpty()) || TokenValidator.isTokenAdmin(token.orEmpty())) {
            call.respond(Products.fetchProducts())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createProduct() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenValidator.isTokenAdmin(token.orEmpty())) {
            val requestBody = call.receive<CreateProductRequest>()
            val product = requestBody.mapToProductsDTO()
            Products.insertProducts(product)
            call.respond(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }
}