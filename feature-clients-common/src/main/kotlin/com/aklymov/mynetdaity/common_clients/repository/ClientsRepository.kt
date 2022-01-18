package com.aklymov.mynetdaity.common_clients.repository

import com.aklymov.mynetdaity.common_clients.entity.Client
import kotlinx.coroutines.flow.Flow

interface ClientsRepository {

    val clientsList: Flow<List<Client>>

    fun getNotCreatedClientId(): Int

    fun getOrCreateClient(id: Int): Client

    fun addClient(client: Client)

    fun updateClient(client: Client)
}
