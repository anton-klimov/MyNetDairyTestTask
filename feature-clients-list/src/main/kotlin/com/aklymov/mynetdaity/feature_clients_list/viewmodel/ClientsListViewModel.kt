package com.aklymov.mynetdaity.feature_clients_list.viewmodel

import androidx.lifecycle.ViewModel
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import kotlinx.coroutines.flow.Flow

class ClientsListViewModel(
    clientsRepository: ClientsRepository
) : ViewModel() {

    val clientsList: Flow<List<Client>> = clientsRepository.clientsList
}
