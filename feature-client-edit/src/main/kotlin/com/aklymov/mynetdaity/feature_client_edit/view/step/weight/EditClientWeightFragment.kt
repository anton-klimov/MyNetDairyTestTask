package com.aklymov.mynetdaity.feature_client_edit.view.step.weight

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientWeightBinding
import com.aklymov.mynetdaity.feature_client_edit.view.step.BaseEditStepFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.weight.EditClientWeightViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.bindProvider
import org.kodein.di.diContext

internal class EditClientWeightFragment : BaseEditStepFragment<FragmentEditClientWeightBinding>(),
    TextWatcher, AdapterView.OnItemSelectedListener {

    override val diContext: DIContext<*> = diContext(this)
    override val di: DI by DI.lazy {
        extend((parentFragment as DIAware).di, copy = Copy.None)
        bindProvider {
            EditClientWeightViewModel(parentViewModel = parentViewModel)
        }
    }

    override val layoutId: Int = R.layout.fragment_edit_client_weight

    private val viewModel: EditClientWeightViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etEditClientWeight.addTextChangedListener(this)
        val currentWeight = viewModel.currentWeight
        if (currentWeight > 0) {
            binding.etEditClientWeight.setText(currentWeight.toString())
        }

        initMeasurmentSpinner()

        launchAndCollectOnStart {
            viewModel
                .measurementChangedWeight
                .filter { it > 0 }
                .collectLatest { weightLb ->
                    binding.etEditClientWeight.setText(
                        getString(R.string.edit_client_weight_template, weightLb)
                    )
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.etEditClientWeight.removeTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // ignored
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!s.isNullOrEmpty()) {
            viewModel.saveWeight(getUserWeightInput(), isLbSelected())
        }
    }

    override fun afterTextChanged(s: Editable?) {
        // ignored
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.onMeasurmentChanged(getUserWeightInput(), isLbSelected())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // ignored
    }

    private fun isLbSelected(): Boolean {
        return binding.sEditClientWeight.selectedItemPosition == 0
    }

    private fun getUserWeightInput(): Float {
        val input = binding.etEditClientWeight.text.toString()
        return if (input.isEmpty()) 0F else input.toFloat()
    }

    private fun initMeasurmentSpinner() {
        binding.sEditClientWeight.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.edit_client_weight_measures,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.sEditClientWeight.setSelection(0)
        binding.sEditClientWeight.onItemSelectedListener = this
    }
}
