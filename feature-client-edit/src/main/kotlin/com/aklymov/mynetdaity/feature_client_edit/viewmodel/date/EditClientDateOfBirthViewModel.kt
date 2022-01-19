package com.aklymov.mynetdaity.feature_client_edit.viewmodel.date

import com.aklymov.mynetdaity.feature_client_edit.viewmodel.BaseEditClientStepViewModel
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.ClientPropertyUpdater

internal class EditClientDateOfBirthViewModel(
    parentViewModel: ClientPropertyUpdater
) : BaseEditClientStepViewModel(parentViewModel) {

    var currentDate: Long
        get() = client.birthDate
        set(value) {
            clientPropertyUpdater.setClient(client.copy(birthDate = value))
            // we really don't know restrictions which applied to date, so let it be always true
            // we disabled future dates at date picker
            val isValidDate = true
            clientPropertyUpdater.setStepCompleted(isValidDate)
        }
}
