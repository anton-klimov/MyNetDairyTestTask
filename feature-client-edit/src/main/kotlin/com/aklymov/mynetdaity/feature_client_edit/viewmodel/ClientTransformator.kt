package com.aklymov.mynetdaity.feature_client_edit.viewmodel

import com.aklymov.mynetdaity.common_clients.entity.Client

interface ClientPropertyUpdater {

    fun getClient(): Client

    fun setClient(client: Client)

    fun setStepCompleted(completed: Boolean)
}
