package ru.poloskun.cache

import ru.poloskun.features.register.RegisterReceiveRemote

object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val tokenList: MutableList<TokenCache> = mutableListOf()
}

data class TokenCache(
    val login: String,
    val token: String
)