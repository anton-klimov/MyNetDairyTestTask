package com.aklymov.mynetdaity.feature_clients_inmemory_repositpry

import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.kodein.di.bindings.ScopeCloseable

/**
 * Simple implementation of ClientsRepository based on in memory storage. This class is useful for
 * test and until some real storage (database or backend) will be implemented.
 * TODO add comment about weak implementation
 */
class InMemoryClientsRepository : ClientsRepository, ScopeCloseable {

    private val clientListStateFlow: MutableStateFlow<List<Client>> = MutableStateFlow(emptyList())
    override val clientsList: Flow<List<Client>> = clientListStateFlow

    override fun getNotCreatedClientId(): Int {
        return Client.DEFAULT_ID
    }

    override fun getOrCreateClient(id: Int): Client {
        return clientListStateFlow.value.find { it.id == id } ?: Client()
    }

    override fun addClient(client: Client) {
        clientListStateFlow.update { currentClientsList ->
            // TODO set a proper id
            currentClientsList.plus(client)
        }
    }

    override fun updateClient(client: Client) {
        clientListStateFlow.update { currentClientsList ->
            val updatedList = currentClientsList.toMutableList()
            val clientToUpdateIndex = currentClientsList.indexOfFirst { it.id == client.id }
            updatedList[clientToUpdateIndex] = client
            updatedList
        }
    }

    override fun close() {
        clientListStateFlow.value = emptyList()
    }
}
