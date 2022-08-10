package ru.poloskun.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.poloskun.features.register.RegisterReceiveRemote

object Users : Table() {
    private val id = Users.integer("id")
    private val login = Users.varchar("login", length = 25)
    private val password = Users.varchar("password", length = 25)
    private val username = Users.varchar("username", length = 25)
    private val email = Users.varchar("email", length = 50)

    fun insert(registerReceiveRemote: RegisterReceiveRemote): UserDTO? {
        transaction {
            Users.insert {
                it[login] = registerReceiveRemote.login
                it[password] = registerReceiveRemote.password
                it[username] = registerReceiveRemote.username
                it[email] = registerReceiveRemote.email
            }
        }
        return fetchUser(registerReceiveRemote.login)
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userRow = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    id = userRow[Users.id],
                    login = userRow[Users.login],
                    password = userRow[password],
                    username = userRow[username],
                    email = userRow[email]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}