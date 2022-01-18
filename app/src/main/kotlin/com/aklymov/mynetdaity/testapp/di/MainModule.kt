package com.aklymov.mynetdaity.testapp.di

import com.aklymov.mynetdaity.common_clients.repository.ClientsRepository
import com.aklymov.mynetdaity.common_di.BaseModule
import com.aklymov.mynetdaity.feature_clients_inmemory_repositpry.InMemoryClientsRepository
import org.kodein.di.DI
import org.kodein.di.android.ActivityRetainedScope
import org.kodein.di.bind
import org.kodein.di.scoped
import org.kodein.di.singleton

internal object MainModule : BaseModule {

    override val instance: DI.Module = DI.Module("MainModule") {
        bind<ClientsRepository>() with scoped(ActivityRetainedScope).singleton {
            InMemoryClientsRepository()
        }
    }
}
