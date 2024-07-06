package com.benkhalifa.republicserviceschallenge.driverUI.driverdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benkhalifa.republicserviceschallenge.domain.Route
import com.benkhalifa.republicserviceschallenge.domain.usecase.GetRouteForDriverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverDetailsViewModel @Inject constructor(
    private val getRouteForDriverUseCase: GetRouteForDriverUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _fetchedRoute = MutableLiveData<Route>()
    val fetchedRoute: LiveData<Route>
        get() = _fetchedRoute

    init {
        val driverId: String = savedStateHandle.get<String>("driverId") ?: ""
        viewModelScope.launch {
            _fetchedRoute.value = getRouteForDriverUseCase.getRouteById(driverId)
        }
    }

}