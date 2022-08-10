package ru.poloskun.database.products

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.poloskun.features.products.CreateProductRequest

object Products : Table() {
    private val id = Products.integer("id")
    private val article = Products.varchar("article", length = 10)
    private val name = Products.varchar("name", length = 100)
    private val quantity = Products.integer("quantity")
    private val purchaseCost = Products.double("purchaseCost")
    private val retailPrice = Products.double("retailPrice")
    private val isBottling = Products.bool("isBottling")


    fun fetchProducts(): List<ProductsDTO> {
        return try {
            transaction {
                Products.selectAll().toList()
                    .map {
                        ProductsDTO(
                            id = it[Products.id],
                            article = it[article],
                            name = it[name],
                            quantity = it[quantity],
                            purchaseCost = it[purchaseCost],
                            retailPrice = it[retailPrice],
                            isBottling = it[isBottling],
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun insertProducts(createRequest: CreateProductRequest) {
        transaction {
            Products.insert {
                it[article] = createRequest.article
                it[name] = createRequest.name
                it[quantity] = createRequest.quantity
                it[purchaseCost] = createRequest.purchaseCost
                it[retailPrice] = createRequest.retailPrice
                it[isBottling] = createRequest.isBottling
            }
        }
    }
}