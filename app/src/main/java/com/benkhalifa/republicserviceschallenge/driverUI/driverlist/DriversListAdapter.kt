package com.benkhalifa.republicserviceschallenge.driverUI.driverlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.databinding.DriverListItemBinding


class DriversListAdapter(private val clickListener: DriverClickListener) :
    ListAdapter<Driver, DriversListAdapter.ViewHolder>(
        DriversDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DriverListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(private val binding: DriverListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(driver: Driver, clickListener: DriverClickListener) {
            binding.driver = driver
            binding.driverClickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class DriversDiffCallback : DiffUtil.ItemCallback<Driver>() {
    override fun areItemsTheSame(oldItem: Driver, newItem: Driver): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Driver, newItem: Driver): Boolean {
        return oldItem == newItem
    }
}

interface DriverClickListener {
    fun onClick(driver: Driver)
}