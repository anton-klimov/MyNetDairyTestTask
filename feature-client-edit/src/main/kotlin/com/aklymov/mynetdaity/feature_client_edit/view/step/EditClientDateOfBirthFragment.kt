package com.aklymov.mynetdaity.feature_client_edit.view.step

import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientWeightBinding
import org.kodein.di.DIContext
import org.kodein.di.diContext

internal class EditClientDateOfBirthFragment : BaseEditStepFragment<FragmentEditClientWeightBinding>() {

    override val diContext: DIContext<*> = diContext(this)
    override val layoutId: Int = R.layout.fragment_edit_client_date_of_birth
}
