package ru.poloskun

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.poloskun.features.login.configureLoginRouting
import ru.poloskun.features.products.configureProductsRouting
import ru.poloskun.features.register.configureRegisterRouting
import ru.poloskun.plugins.configureRouting
import ru.poloskun.plugins.configureSerialization

fun main() {
//    Database.connect(url = "jdbc:postgresql://localhost:5432/poloskun", driver = "org.postgresql.Driver",
//    user = "postgres", password = "pgLexicon1984")

//    val config = HikariConfig("hikari.properties")
//    val dataSource = HikariDataSource(config)
//    Database.connect(dataSource)

    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
        configureSerialization()
        configureLoginRouting()
        configureRegisterRouting()
        configureProductsRouting()
    }.start(wait = true)
}