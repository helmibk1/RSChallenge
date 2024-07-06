package com.benkhalifa.republicserviceschallenge.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.benkhalifa.republicserviceschallenge.R
import com.benkhalifa.republicserviceschallenge.driverUI.driverlist.DriversListAdapter
import com.benkhalifa.republicserviceschallenge.domain.Driver


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Driver>?) {
    recyclerView.adapter?.let { adapter ->
        if (adapter is DriversListAdapter) {
            adapter.submitList(data)
        }
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, resultOf: ResultOf?) {
    when (resultOf) {
        is ResultOf.Loading -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        is ResultOf.Error -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.connection_error)
        }

        is ResultOf.Success -> {
            statusImageView.visibility = View.GONE
        }

        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}