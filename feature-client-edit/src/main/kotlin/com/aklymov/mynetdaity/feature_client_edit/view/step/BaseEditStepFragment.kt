package com.aklymov.mynetdaity.feature_client_edit.view.step

import androidx.databinding.ViewDataBinding
import com.aklymov.mynetdaity.common_ui.BaseDataBindingFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.viewmodel.viewModel

internal abstract class BaseEditStepFragment<T: ViewDataBinding> : BaseDataBindingFragment<T>(), DIAware {

    override val di: DI by DI.lazy {
        extend((parentFragment as DIAware).di, copy = Copy.None)
    }

    internal val viewModel: EditClientViewModel by viewModel(ownerProducer = {
        requireParentFragment()
    })

    // TODO depending on step create ui and update client
}
