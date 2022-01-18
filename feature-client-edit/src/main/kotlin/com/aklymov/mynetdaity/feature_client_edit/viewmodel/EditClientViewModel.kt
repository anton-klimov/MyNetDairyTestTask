package com.aklymov.mynetdaity.feature_client_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class EditClientViewModel(
    clientId: Int,
    private val clientsRepository: ClientsRepository
) : ViewModel() {

    private val steps: Array<EditStep> = arrayOf(
        EditStep.CHANGE_WEIGHT,          // TODO lb or kg, convert
        EditStep.CHANGE_DATE_OF_BIRTH,
        EditStep.CHANGE_IMAGE
    )

    private val currentEditStepFlow: MutableStateFlow<EditStep> = MutableStateFlow(steps.first())
    val currentEditStep: Flow<EditStep> = currentEditStepFlow

    private val stepIndicatorFlow: MutableStateFlow<String> = MutableStateFlow("")
    val stepIndicator: Flow<String> = stepIndicatorFlow

    private val closeScreenFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    val closeScreen: Flow<Unit> = closeScreenFlow

    private var currentStepIndex: Int = 0
    private val currentStep: EditStep
        get() = steps[currentStepIndex]

    var currentClient: Client
        private set

    init {
        currentClient = clientsRepository.getOrCreateClient(clientId)
        viewModelScope.launch {
            currentEditStepFlow
                .collect {
                    stepIndicatorFlow.emit(buildIndicator())
                }
        }
    }

    fun onPositiveClicked() {
        viewModelScope.launch {
            currentStepIndex = currentStepIndex.inc()
            if (currentStepIndex > steps.lastIndex) {
                clientsRepository.updateClient(currentClient)
                closeScreenFlow.emit(Unit)
            } else {
                currentEditStepFlow.emit(currentStep)
            }
        }

    }

    fun onNegativeClicked() {
        viewModelScope.launch {
            currentStepIndex = currentStepIndex.dec()
            if (currentStepIndex < 0) {
                closeScreenFlow.emit(Unit)
            } else {
                currentEditStepFlow.emit(currentStep)
            }
        }

    }

    fun updateClientProperty(value: Any) {
        currentClient = when (currentStep) {
            EditStep.CHANGE_WEIGHT -> currentClient.copy(weightKg = value as Int)
            EditStep.CHANGE_DATE_OF_BIRTH -> currentClient.copy(birthDate = value as Long)
            EditStep.CHANGE_IMAGE -> currentClient.copy(imageUri = value as String)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    private fun buildIndicator(): String {
        return "%d/%d".format(currentStepIndex + 1, steps.size)
    }
}
