package com.aklymov.mynetdaity.feature_client_edit.view

import android.os.Bundle
import android.view.View
import com.aklymov.mynetdaity.common_ui.BaseDataBindingFragment
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientBinding
import com.aklymov.mynetdaity.feature_client_edit.di.EditClientModule
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.subDI
import org.kodein.di.android.x.closestDI
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.bindProvider
import org.kodein.di.diContext

class EditClientFragment : BaseDataBindingFragment<FragmentEditClientBinding>(), DIAware {

    companion object {
        private const val ARG_USER_ID = "userId"

        fun newInstance(clientId: Int): EditClientFragment {
            val args = Bundle(1).apply {
                putInt(ARG_USER_ID, clientId)
            }
            return EditClientFragment().apply {
                arguments = args
            }
        }
    }

    override val diContext: DIContext<*> = diContext(this)
    override val di: DI by subDI(closestDI()) {
        import(EditClientModule.instance)
        bindProvider(EditClientModule.CLIENT_ID_TAG) {
            requireArguments().getInt(ARG_USER_ID)
        }
    }

    override val layoutId: Int = R.layout.fragment_edit_client

    private val viewModel: EditClientViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bEditClientPositive.setOnClickListener {
            viewModel.onPositiveClicked()
        }
        binding.bEditClientNegative.setOnClickListener {
            viewModel.onNegativeClicked()
        }
        with(binding.vpEditClient) {
            isUserInputEnabled = false
            adapter = EditClientStepsAdapter(this@EditClientFragment)
        }
        binding.spiClientEdit.attachToPager(binding.vpEditClient)

        launchAndCollectOnStart {
            viewModel
                .closeScreen
                .collectLatest {
                    parentFragmentManager.popBackStack()
                }
        }
        launchAndCollectOnStart {
            viewModel
                .currentEditStep
                .collectLatest { step ->
                    binding.vpEditClient.currentItem = step.ordinal
                }
        }

        launchAndCollectOnStart {
            viewModel
                .currentStepCompleted
                .collectLatest {
                    binding.bEditClientPositive.isEnabled = it
                }
        }

        launchAndCollectOnStart {
            viewModel
                .showDoneButton
                .collectLatest {
                    binding.bEditClientPositive.setText(
                        if (it) R.string.edit_client_done else R.string.edit_client_next
                    )
                }
        }
    }
}
