package com.aklymov.mynetdaity.feature_client_edit.view.step

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientWeightBinding
import org.kodein.di.DIContext
import org.kodein.di.diContext

internal class EditClientWeightFragment : BaseEditStepFragment<FragmentEditClientWeightBinding>(),
    TextWatcher {

    override val diContext: DIContext<*> = diContext(this)
    override val layoutId: Int = R.layout.fragment_edit_client_weight

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weightKg = viewModel.currentClient.weightKg
        if (weightKg > 0) {
            binding.etEditClientWeight.setText(weightKg.toString())
        }
        binding.etEditClientWeight.addTextChangedListener(this)
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
            viewModel.updateClientProperty(s.toString().toInt())
        }
    }

    override fun afterTextChanged(s: Editable?) {
        // ignored
    }
}
