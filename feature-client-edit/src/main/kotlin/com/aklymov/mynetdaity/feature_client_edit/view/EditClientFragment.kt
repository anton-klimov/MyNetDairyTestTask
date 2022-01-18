package com.aklymov.mynetdaity.feature_client_edit.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aklymov.mynetdaity.common_ui.BaseDataBindingFragment
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientBinding
import com.aklymov.mynetdaity.feature_client_edit.di.EditClientModule
import com.aklymov.mynetdaity.feature_client_edit.view.step.BaseEditStepFragment
import com.aklymov.mynetdaity.feature_client_edit.view.step.EditClientDateOfBirthFragment
import com.aklymov.mynetdaity.feature_client_edit.view.step.EditClientWeightFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditClientViewModel
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditStep
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.subDI
import org.kodein.di.android.x.closestDI
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.bindProvider
import org.kodein.di.diContext

// TODO preserve current step
// TODO create two fragments for edit and create
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

    // TODO leave a comment that we can create an api module to distinguish implementation
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .closeScreen
                    .collectLatest {
                        parentFragmentManager.popBackStack()
                    }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .currentEditStep
                    .collect { step ->
                        // TODO show title and step
                        val fragment: BaseEditStepFragment<*> = when(step) {
                            EditStep.CHANGE_WEIGHT -> EditClientWeightFragment()
                            EditStep.CHANGE_DATE_OF_BIRTH -> EditClientDateOfBirthFragment()
                            EditStep.CHANGE_IMAGE -> TODO()
                        }
                        childFragmentManager
                            .beginTransaction()
                            .replace(R.id.flEditClientFragmentsRoot, fragment)
                            .commit()
                    }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel
                    .stepIndicator
                    .collect {
                        binding.tvCurrentStep.text = it
                    }
            }
        }

        binding.bEditClientPositive.setOnClickListener {
            viewModel.onPositiveClicked()
        }
        binding.bEditClientNegative.setOnClickListener {
            viewModel.onNegativeClicked()
        }
    }
}
