package com.aklymov.mynetdaity.feature_clients_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

internal class ClientsListViewModel(
    private val clientsRepository: ClientsRepository
) : ViewModel() {

    val clientsList: Flow<List<Client>> = clientsRepository.clientsList

    private val openEditClientScreenFlow: MutableSharedFlow<Int> = MutableSharedFlow()
    val openEditClientScreen: Flow<Int> = openEditClientScreenFlow

    fun onAddClientClicked() {
        viewModelScope.launch {
            openEditClientScreenFlow.emit(clientsRepository.getNotCreatedClientId())
        }
    }

    fun onEditClientClicked(clientId: Int) {
        viewModelScope.launch {
            openEditClientScreenFlow.emit(clientId)
        }
    }
}
