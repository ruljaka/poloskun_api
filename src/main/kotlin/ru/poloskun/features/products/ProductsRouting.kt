package ru.poloskun.features.products

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.poloskun.cache.InMemoryCache
import ru.poloskun.cache.TokenCache
import java.util.UUID

fun Application.configureProductsRouting() {

    routing {
        get("/products/get") {
            ProductsController(call).fetchAllProducts()
        }
        post("/products/create") {
            ProductsController(call).createProduct()
        }
    }
}

