package com.aklymov.mynetdaity.feature_client_edit.di

import com.aklymov.mynetdaity.common_di.BaseModule
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import org.kodein.di.DI
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.scoped
import org.kodein.di.singleton

internal object EditClientModule : BaseModule {

    const val CLIENT_ID_TAG = "clientId"

    override val instance: DI.Module = DI.Module("EditClientModule") {
        bind<EditClientViewModel>() with scoped(ActivityRetainedScope).singleton {
            EditClientViewModel(
                clientId = instance(CLIENT_ID_TAG),
                clientsRepository = instance()
            )
        }
    }
}
