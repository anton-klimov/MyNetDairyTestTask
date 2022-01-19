package com.aklymov.mynetdaity.feature_clients_list.view

import android.content.res.Resources
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aklymov.mynetdaity.common_clients.entity.Client
import com.aklymov.mynetdaity.feature_clients_list.R
import com.aklymov.mynetdaity.feature_clients_list.databinding.ItemClientBinding
import com.squareup.picasso.Picasso

internal class ClientViewHolder(
    itemView: View,
    private val listener: ClientEditClickListener
    ) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemClientBinding = DataBindingUtil.bind(itemView)!!
    private val resoruces: Resources
        get() = itemView.resources

    fun bindData(client: Client) {
        Picasso
            .get()
            .load(client.imageUri)
            .placeholder(R.drawable.ic_client_photo_placeholder)
            .into(binding.ivItemClientPhoto)

        binding.tvItemClientDateOfBirthValue.text = resoruces.getString(
            R.string.date_of_birth_template,
            client.birthDate
        )
        binding.tvItemClientWeightValue.text = resoruces.getString(R.string.weight_template, client.weightLb)
        binding.bItemClientEdit.setOnClickListener {
            listener.onEditClick(client.id)
        }
    }
}
