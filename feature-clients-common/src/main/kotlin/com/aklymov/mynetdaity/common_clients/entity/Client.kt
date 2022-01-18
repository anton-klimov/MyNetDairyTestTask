package com.aklymov.mynetdaity.common_clients.entity

data class Client(
    val id: Int = DEFAULT_ID,
    val weightKg: Int = 0,
    val imageUri: String? = null,
    val birthDate: Long = 0
) {
    companion object {
        const val DEFAULT_ID = -1
    }
}
