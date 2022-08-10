package ru.poloskun.features.register

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.poloskun.cache.InMemoryCache
import ru.poloskun.cache.TokenCache
import ru.poloskun.utils.isValidEmail
import java.util.UUID

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}


