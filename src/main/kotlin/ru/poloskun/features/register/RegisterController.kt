package ru.poloskun.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.poloskun.database.tokens.TokenDTO
import ru.poloskun.database.tokens.Tokens
import ru.poloskun.database.users.UserDTO
import ru.poloskun.database.users.Users
import ru.poloskun.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid")
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                val newUser = Users.insert(registerReceiveRemote)
                newUser?.let {
                    Tokens.insert(
                        TokenDTO(
                            token = token,
                            id = it.id,
                            login = registerReceiveRemote.login,
                        )
                    )
                    call.respond(RegisterResponseRemote(token = token))
                }
            } catch (e: ExposedSQLException) {
                e.stackTrace
                call.respond(HttpStatusCode.Conflict, "User already exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }
        }
    }
}