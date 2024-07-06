package com.benkhalifa.republicserviceschallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.benkhalifa.republicserviceschallenge.database.DatabaseDriver
import com.benkhalifa.republicserviceschallenge.database.TransportDatabase
import com.benkhalifa.republicserviceschallenge.database.asDriverDomainModel
import com.benkhalifa.republicserviceschallenge.database.asRouteDomainModel
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.domain.Route
import com.benkhalifa.republicserviceschallenge.network.DriversApi
import com.benkhalifa.republicserviceschallenge.network.asDatabaseModelDrivers
import com.benkhalifa.republicserviceschallenge.network.asDatabaseModelRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import com.benkhalifa.republicserviceschallenge.util.ResultOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransportRepository @Inject constructor(private val database: TransportDatabase) {

    private val databaseDrivers: LiveData<List<DatabaseDriver>> =
        database.transportDao().getDriversLiveData()
    val drivers: LiveData<List<Driver>> = databaseDrivers.map() {
        it.asDriverDomainModel()
    }

    private val _resultOf = MutableLiveData<ResultOf>()
    val resultOf: LiveData<ResultOf>
        get() = _resultOf

    suspend fun fetchDataAndSave() {
        withContext(Dispatchers.IO) {
            val driverCount = database.transportDao().getDriverCount()
            val routeCount = database.transportDao().getRouteCount()
            if (driverCount == 0 || routeCount == 0) {
                try {
                    _resultOf.postValue(ResultOf.Loading)
                    val dataObject = DriversApi.retrofitService.getData().await()
                    database.transportDao().insertAllDrivers(dataObject.asDatabaseModelDrivers())
                    database.transportDao().insertAllRoutes(dataObject.asDatabaseModelRoutes())
                    _resultOf.postValue(ResultOf.Success("Success: ${dataObject.drivers.size} drivers saved"))
                } catch (t: Throwable) {
                    _resultOf.postValue(ResultOf.Error("Failure: ${t.message}"))
                }
            } else {
                _resultOf.postValue(ResultOf.Success("Success: $driverCount drivers retrieved from database"))
            }
        }
    }

    suspend fun getRoutesData(): List<Route> {
        return withContext(Dispatchers.IO) {
            database.transportDao().getRoutes().asRouteDomainModel()
        }
    }

    suspend fun getDriversData(): List<Driver> {
        return withContext(Dispatchers.IO) {
            database.transportDao().getDrivers().asDriverDomainModel()
        }
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            database.transportDao().clearAllDrivers()
            database.transportDao().clearAllRoutes()
        }
        fetchDataAndSave()
    }
}