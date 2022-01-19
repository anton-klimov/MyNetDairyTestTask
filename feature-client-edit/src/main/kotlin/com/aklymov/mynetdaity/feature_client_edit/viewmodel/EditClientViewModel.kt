package com.aklymov.mynetdaity.feature_client_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class EditClientViewModel(
    clientId: Int,
    private val clientsRepository: ClientsRepository
) : ViewModel(), ClientPropertyUpdater {

    private val steps: Array<EditStep> = arrayOf(
        EditStep.CHANGE_WEIGHT,
        EditStep.CHANGE_DATE_OF_BIRTH,
        EditStep.CHANGE_IMAGE
    )

    private val currentEditStepFlow: MutableStateFlow<EditStep> = MutableStateFlow(steps.first())
    val currentEditStep: Flow<EditStep> = currentEditStepFlow

    private val closeScreenFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    val closeScreen: Flow<Unit> = closeScreenFlow

    private val currentStepCompletedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val currentStepCompleted: Flow<Boolean> = currentStepCompletedFlow

    val showDoneButton: Flow<Boolean> = currentEditStep
        .map { it == steps.last() }

    private var currentStepIndex: Int = 0
    private val currentStep: EditStep
        get() = steps[currentStepIndex]

    var currentClient: Client
        private set

    init {
        currentClient = clientsRepository.getOrCreateClient(clientId)
    }

    fun onPositiveClicked() {
        viewModelScope.launch {
            currentStepIndex = currentStepIndex.inc()
            currentStepCompletedFlow.emit(false)
            if (currentStepIndex > steps.lastIndex) {
                clientsRepository.updateOrAddClient(currentClient)
                closeScreenFlow.emit(Unit)
            } else {
                currentEditStepFlow.emit(currentStep)
            }
        }
    }

    fun onNegativeClicked() {
        viewModelScope.launch {
            currentStepIndex = currentStepIndex.dec()
            currentStepCompletedFlow.emit(true)
            if (currentStepIndex < 0) {
                closeScreenFlow.emit(Unit)
            } else {
                currentEditStepFlow.emit(currentStep)
            }
        }
    }

    override fun setStepCompleted(completed: Boolean) {
        viewModelScope.launch {
            currentStepCompletedFlow.emit(completed)
        }
    }

    override fun getClient(): Client {
        return currentClient
    }

    override fun setClient(client: Client) {
        currentClient = client
    }
}
