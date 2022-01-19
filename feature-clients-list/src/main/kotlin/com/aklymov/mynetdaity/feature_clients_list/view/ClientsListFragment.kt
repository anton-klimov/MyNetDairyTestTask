package com.aklymov.mynetdaity.feature_clients_list.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.common_ui.BaseDataBindingFragment
import com.aklymov.mynetdaity.feature_client_edit.view.EditClientFragment
import com.aklymov.mynetdaity.feature_clients_list.R
import com.aklymov.mynetdaity.feature_clients_list.databinding.FragmentClientsListBinding
import com.aklymov.mynetdaity.feature_clients_list.di.ClientsListModule
import com.aklymov.mynetdaity.feature_clients_list.viewmodel.ClientsListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.subDI
import org.kodein.di.android.x.closestDI
import org.kodein.di.android.x.viewmodel.viewModel
import org.kodein.di.diContext

class ClientsListFragment : BaseDataBindingFragment<FragmentClientsListBinding>(), DIAware,
    ClientEditClickListener {

    override val diContext: DIContext<Fragment> = diContext(this)
    override val di: DI by subDI(closestDI()) {
        import(ClientsListModule.instance)
    }

    override val layoutId: Int = R.layout.fragment_clients_list

    private val viewModel: ClientsListViewModel by viewModel()

    private val clientsAdapter: ClientsListAdapter = ClientsListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.rvClientsList) {
            adapter = clientsAdapter
            setHasFixedSize(true)
            addItemDecoration(
                ItemsDividerDecoration(resources.getDimension(R.dimen.clients_list_divider_height))
            )
        }

        launchAndCollectOnStart {
            viewModel
                .clientsList
                .collectLatest {
                    showClients(it)
                }
        }
        launchAndCollectOnStart {
            viewModel
                .openEditClientScreen
                .collect {
                    openEditScreen(it)
                }
        }

        binding.bClientsListAdd.setOnClickListener {
            viewModel.onAddClientClicked()
        }
    }

    private fun showClients(clientsList: List<Client>) {
        binding.tvClientsListEmptyView.visibility = if (clientsList.isEmpty()) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
        clientsAdapter.submitList(clientsList)
    }

    private fun openEditScreen(clientId: Int) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.flMainContainer, EditClientFragment.newInstance(clientId))
            .addToBackStack(null)
            .commit()
    }

    private class ItemsDividerDecoration(strokeWidth: Float) : RecyclerView.ItemDecoration() {

        private val linePaint: Paint

        init {
            linePaint = Paint()
            linePaint.color = Color.BLACK
            linePaint.strokeWidth = strokeWidth
        }

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                c.drawLine(0F, child.y, parent.width.toFloat(), child.y, linePaint)
            }
        }
    }

    override fun onEditClick(id: Int) {
        viewModel.onEditClientClicked(id)
    }
}
