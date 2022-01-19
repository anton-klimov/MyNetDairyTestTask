package com.aklymov.mynetdaity.feature_client_edit.di

import com.aklymov.mynetdaity.common_di.BaseModule
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

internal object EditClientModule : BaseModule {

    const val CLIENT_ID_TAG = "clientId"

    override val instance: DI.Module = DI.Module("EditClientModule") {
        bindProvider {
            EditClientViewModel(
                clientId = instance(CLIENT_ID_TAG),
                clientsRepository = instance()
            )
        }
    }
}
