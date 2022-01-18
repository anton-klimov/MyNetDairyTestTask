package com.aklymov.mynetdaity.feature_client_edit.viewmodel

import com.aklymov.mynetdaity.common_clients.entity.Client

interface ClientTransformator<T> {

    fun transform(client: Client, value: T): Client
}
