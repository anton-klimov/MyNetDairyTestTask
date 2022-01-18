package com.aklymov.mynetdaity.feature_clients_list.di

import com.aklymov.mynetdaity.common_di.BaseModule
import com.aklymov.mynetdaity.feature_clients_list.viewmodel.ClientsListViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

object ClientsListModule : BaseModule {

    override val instance: DI.Module = DI.Module("ClientsListModule") {
        bindProvider {
            ClientsListViewModel(
                clientsRepository = instance()
            )
        }
    }
}
