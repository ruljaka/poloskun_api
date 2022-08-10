package ru.poloskun.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table() {

    private val token = Tokens.varchar("token", length = 50)
    private val id = Tokens.integer("id")
    private val login = Tokens.varchar("login", length = 25)


    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[token] = tokenDTO.token
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
            }
        }
    }

    fun fetchTokens(): List<TokenDTO> {
        return try {
            transaction {
                Tokens.selectAll().toList()
                    .map {
                        TokenDTO(
                            token = it[Tokens.token],
                            id = it[Tokens.id],
                            login = it[Tokens.login]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAdminToken(): String {
        return transaction {
            val admin = Tokens.select {
                Tokens.login.eq("admin")
            }.single()
            admin[token]
        }
    }
}