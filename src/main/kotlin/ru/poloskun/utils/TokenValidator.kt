package ru.poloskun.utils

import ru.poloskun.database.tokens.Tokens

object TokenValidator {
    fun isTokenValid(token: String): Boolean = Tokens.fetchTokens().firstOrNull { it.token == token } != null

    //fun isTokenAdmin(token: String): Boolean = token == "d35ca962-7a23-4104-b1ce-2343f6f96093"
    fun isTokenAdmin(token: String): Boolean = token == "d00f5f04-78b2-48af-866a-977ae1a4d1ee"
}