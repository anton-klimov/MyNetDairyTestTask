package com.aklymov.mynetdaity.feature_client_edit.viewmodel.image

import androidx.lifecycle.viewModelScope
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.BaseEditClientStepViewModel
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.ClientPropertyUpdater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class EditClientImageViewModel(
    parentViewModel: ClientPropertyUpdater
) : BaseEditClientStepViewModel(parentViewModel) {

    private val currentImageStateFlow: MutableStateFlow<String?> = MutableStateFlow(client.imageUri)
    val currentImage: Flow<String?> = currentImageStateFlow

    fun setUserImage(imagePath: String) {
        viewModelScope.launch {
            clientPropertyUpdater.setClient(client.copy(imageUri = imagePath))
            val isValidDate = true // any image is acceptable
            clientPropertyUpdater.setStepCompleted(isValidDate)
            currentImageStateFlow.emit(imagePath)
        }
    }
}
