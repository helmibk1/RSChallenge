package com.benkhalifa.republicserviceschallenge.network

import com.benkhalifa.republicserviceschallenge.database.DatabaseDriver
import com.benkhalifa.republicserviceschallenge.database.DatabaseRoute
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.domain.Route


data class NetworkResponse(
    val drivers: List<NetworkDriver>,
    val routes: List<NetworkRoute>
)

data class NetworkDriver(
    val id: String,
    val name: String
)

data class NetworkRoute(
    val id: Int,
    val type: String,
    val name: String
)


fun NetworkResponse.asDatabaseModelDrivers(): List<DatabaseDriver> {
    return drivers.map {
        DatabaseDriver(
            id = it.id,
            name = it.name
        )
    }
}

fun NetworkResponse.asDatabaseModelRoutes(): List<DatabaseRoute> {
    return routes.map {
        DatabaseRoute(
            id = it.id,
            type = it.type,
            name = it.name
        )
    }
}



