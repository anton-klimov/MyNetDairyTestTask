package com.aklymov.mynetdaity.feature_client_edit.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aklymov.mynetdaity.feature_client_edit.view.step.date.EditClientDateOfBirthFragment
import com.aklymov.mynetdaity.feature_client_edit.view.step.image.EditClientImageFragment
import com.aklymov.mynetdaity.feature_client_edit.view.step.weight.EditClientWeightFragment
import com.aklymov.mynetdaity.feature_client_edit.viewmodel.EditStep

class EditClientStepsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return EditStep.values().size
    }

    override fun createFragment(position: Int): Fragment {
        val editStep = EditStep.values()[position]
        return when(editStep) {
            EditStep.CHANGE_WEIGHT -> EditClientWeightFragment()
            EditStep.CHANGE_DATE_OF_BIRTH -> EditClientDateOfBirthFragment()
            EditStep.CHANGE_IMAGE -> EditClientImageFragment()
        }
    }
}
