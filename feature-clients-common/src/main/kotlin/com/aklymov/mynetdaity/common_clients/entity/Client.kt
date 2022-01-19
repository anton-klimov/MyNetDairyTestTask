package com.aklymov.mynetdaity.common_clients.entity

data class Client(
    val id: Int = DEFAULT_ID,
    val weightLb: Float = 0F,
    val imageUri: String? = null,
    val birthDate: Long = 0
) {
    companion object {
        const val DEFAULT_ID = -1
    }
}
