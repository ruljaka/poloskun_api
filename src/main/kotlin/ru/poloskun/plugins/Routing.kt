package ru.poloskun.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String
)


fun Application.configureRouting() {

    routing {
        get("/products") {
            //call.respondText("Hello World!")
            call.respond(
                listOf(
                    Product(1, "Lesol Wash Universal Гель для стирки (универсальный)"),
                    Product(2, "Lesol Wash Profi Гель концентрат для стирки"),
                )
            )
        }
    }
}

