package ru.poloskun.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import ru.poloskun.database.tokens.TokenDTO
import ru.poloskun.database.tokens.Tokens
import ru.poloskun.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {

    companion object {
        const val ADMIN_LOGIN = "admin"
        const val ADMIN_TOKEN = "admin"
    }

    suspend fun performLogin() {
        val request = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(request.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            val isAdmin = request.login == ADMIN_LOGIN
            val isPasswordCorrect = request.password == userDTO.password

            val token = if (isAdmin) Tokens.getAdminToken() else UUID.randomUUID().toString()

            if (isPasswordCorrect) {
                if(!isAdmin){
                    Tokens.insert(
                        TokenDTO(
                            token = token,
                            id = userDTO.id,
                            login = request.login,
                        )
                    )
                }

                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}