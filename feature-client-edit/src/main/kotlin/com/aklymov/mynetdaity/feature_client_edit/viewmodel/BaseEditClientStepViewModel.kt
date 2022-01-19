package com.aklymov.mynetdaity.feature_client_edit.viewmodel

import androidx.lifecycle.ViewModel
import com.aklymov.mynetdaity.common_clients.entity.Client

internal abstract class BaseEditClientStepViewModel(
    protected val clientPropertyUpdater: ClientPropertyUpdater
) : ViewModel() {

    protected val client: Client
        get() = clientPropertyUpdater.getClient()
}
