package com.benkhalifa.republicserviceschallenge.driverUI.driverdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.benkhalifa.republicserviceschallenge.databinding.FragmentDriverDetailBinding
import com.benkhalifa.republicserviceschallenge.domain.usecase.GetRouteForDriverUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DriverDetailFragment : Fragment() {

    @Inject
    lateinit var getRouteForDriverUseCase: GetRouteForDriverUseCase

    private val viewModel: DriverDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDriverDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Driver Details"

        binding.viewModel = viewModel

        return binding.root
    }

}