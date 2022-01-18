package com.aklymov.mynetdaity.feature_clients_list.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.feature_clients_list.R

class ClientsListAdapter : ListAdapter<Client, ClientViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        return ClientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    private class DiffCallback : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem == newItem
        }
    }
}
