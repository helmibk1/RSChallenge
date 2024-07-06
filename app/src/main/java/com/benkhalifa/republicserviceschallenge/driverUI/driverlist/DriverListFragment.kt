package com.benkhalifa.republicserviceschallenge.driverUI.driverlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.benkhalifa.republicserviceschallenge.R
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.databinding.FragmentDriverListBinding
import com.benkhalifa.republicserviceschallenge.util.ResultOf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DriverListFragment : Fragment() {

    private val viewModel: DriverListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDriverListBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Drivers List"

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = DriversListAdapter(object : DriverClickListener {
            override fun onClick(driver: Driver) {
                viewModel.onDriverClick(driver.id)
            }
        })
        binding.driversList.adapter = adapter

        viewModel.navigateToDriverDetail.observe(viewLifecycleOwner) { driverId ->
            driverId?.let {
                this.findNavController().navigate(
                    DriverListFragmentDirections.actionDriverListFragment2ToDriverDetailFragment(it)
                )
                viewModel.onNavigationDone()
            }
        }

        viewModel.resultOf.observe(viewLifecycleOwner) { res ->
            res?.let {
                when (it) {
                    is ResultOf.Success -> { Toast.makeText(context, (res as ResultOf.Success).message, Toast.LENGTH_SHORT).show()
                        viewModel.resetResultStatus()
                    }

                    is ResultOf.Error -> {
                        Toast.makeText(context, (res as ResultOf.Error).message, Toast.LENGTH_SHORT).show()
                        viewModel.resetResultStatus()
                    }
                    else -> {}
                }
            }
        }

        setupMenu()
        return binding.root
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.filter_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_sort -> {
                        viewModel.toggleSortByLastName()
                        true
                    }

                    R.id.action_refresh -> {
                        viewModel.refreshData()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}