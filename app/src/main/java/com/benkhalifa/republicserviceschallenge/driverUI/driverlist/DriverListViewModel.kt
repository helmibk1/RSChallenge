package com.benkhalifa.republicserviceschallenge.driverUI.driverlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.domain.usecase.GetDriversSortedUseCase
import com.benkhalifa.republicserviceschallenge.repository.TransportRepository
import com.benkhalifa.republicserviceschallenge.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverListViewModel @Inject constructor(
    private val transportRepository: TransportRepository,
    private val getDriversSortedUseCase: GetDriversSortedUseCase
) : ViewModel() {

    private val _navigateToDriverDetail = MutableLiveData<String>()
    val navigateToDriverDetail: LiveData<String>
        get() = _navigateToDriverDetail

    private val _resultOf: MutableLiveData<ResultOf> =
        transportRepository.resultOf as MutableLiveData<ResultOf>
    val resultOf: LiveData<ResultOf>
        get() = _resultOf

    fun resetResultStatus() {
        _resultOf.value = null
    }

    private val isSortByLastName = MutableLiveData<Boolean>().apply { value = false }

    init {
        getData()
    }

    private val _drivers = transportRepository.drivers as MutableLiveData
    val drivers: LiveData<List<Driver>>
        get() = _drivers


    fun toggleSortByLastName() {
        isSortByLastName.value = isSortByLastName.value?.not()
        if (isSortByLastName.value == true) {
            viewModelScope.launch {
                _drivers.value = getDriversSortedUseCase.getDriversSortedByLastName()
            }
        }
    }

    private fun getData() {
        viewModelScope.launch {
            transportRepository.fetchDataAndSave()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            transportRepository.refreshData()
        }
    }

    fun onDriverClick(driverId: String) {
        _navigateToDriverDetail.value = driverId
    }

    fun onNavigationDone() {
        _navigateToDriverDetail.value = null
    }
}
