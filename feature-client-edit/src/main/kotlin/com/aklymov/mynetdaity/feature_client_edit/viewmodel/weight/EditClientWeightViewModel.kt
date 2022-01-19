package com.aklymov.mynetdaity.feature_client_edit.viewmodel.weight

import androidx.lifecycle.viewModelScope
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.BaseEditClientStepViewModel
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private const val KG_TO_LB_FACTOR: Float = 2.20462262F
private const val MIN_WEIGHT: Float = 22F
private const val MAX_WEIGHT: Float = 400F

internal class EditClientWeightViewModel(
    parentViewModel: EditClientViewModel
) : BaseEditClientStepViewModel(parentViewModel) {

    val currentWeight: Float
        get() = client.weightLb
    
    private val measurementChangedWeightSharedFlow: MutableSharedFlow<Float> = MutableSharedFlow()
    val measurementChangedWeight: Flow<Float> = measurementChangedWeightSharedFlow

    private var isLbMeasurmentSelected: Boolean = true

    fun saveWeight(userWeightInput: Float, isLbSelected: Boolean) {
        viewModelScope.launch {
            val userWeightLb = if (isLbSelected) userWeightInput else convertWeightToLb(userWeightInput)
            clientPropertyUpdater.setClient(client.copy(weightLb = userWeightLb))
            clientPropertyUpdater.setStepCompleted((MIN_WEIGHT..MAX_WEIGHT).contains(userWeightLb))
        }
    }

    fun onMeasurmentChanged(userWeightInput: Float, isLbSelected: Boolean) {
        if (isLbMeasurmentSelected == isLbSelected) {
            return
        }
        isLbMeasurmentSelected = isLbSelected

        viewModelScope.launch {
            val convertedWeight = if (isLbSelected) {
                convertWeightToLb(userWeightInput)
            } else {
                convertWeightToKg(userWeightInput)
            }
            measurementChangedWeightSharedFlow.emit(convertedWeight)
        }
    }

    private fun convertWeightToKg(weight: Float): Float {
        return weight / KG_TO_LB_FACTOR
    }

    private fun convertWeightToLb(weight: Float): Float {
        return weight * KG_TO_LB_FACTOR
    }
}
