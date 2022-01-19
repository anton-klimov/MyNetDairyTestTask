package com.aklymov.mynetdaity.feature_client_edit.view.step.date

import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.aklymov.mynetdaity.feature_client_edit.R
import com.aklymov.mynetdaity.feature_client_edit.databinding.FragmentEditClientDateOfBirthBinding
import com.aklymov.mynetdaity.feature_client_edit.view.step.BaseEditStepFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.date.EditClientDateOfBirthViewModel
import org.kodein.di.Copy
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.bindProvider
import org.kodein.di.diContext
import java.util.Calendar
import java.util.Date

internal class EditClientDateOfBirthFragment : BaseEditStepFragment<FragmentEditClientDateOfBirthBinding>(),
    DatePicker.OnDateChangedListener {

    override val diContext: DIContext<*> = diContext(this)
    override val di: DI by DI.lazy {
        extend((parentFragment as DIAware).di, copy = Copy.None)
        bindProvider {
            EditClientDateOfBirthViewModel(parentViewModel = parentViewModel)
        }
    }

    override val layoutId: Int = R.layout.fragment_edit_client_date_of_birth

    private val viewModel: EditClientDateOfBirthViewModel by viewModel()
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePicker()
    }

    private fun initDatePicker() {
        binding.dpEditClientDateOfBirth.maxDate = System.currentTimeMillis()
        calendar.time = Date(viewModel.currentDate)
        binding.dpEditClientDateOfBirth.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            this
        )
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.currentDate = calendar.timeInMillis
    }
}
