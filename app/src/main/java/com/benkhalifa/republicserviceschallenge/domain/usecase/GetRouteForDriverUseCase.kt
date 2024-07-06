package com.benkhalifa.republicserviceschallenge.domain.usecase

import com.benkhalifa.republicserviceschallenge.domain.Route
import com.benkhalifa.republicserviceschallenge.repository.TransportRepository
import javax.inject.Inject

class GetRouteForDriverUseCase @Inject constructor(private val transportRepository: TransportRepository) {
    suspend fun getRouteById(driverId: String): Route? {
        val driverIdInt = driverId.toIntOrNull() ?: return null

        val routes: List<Route> = transportRepository.getRoutesData()

        return when {
            routes.any { it.id == driverIdInt } -> routes.find { it.id == driverIdInt }
            driverIdInt % 2 == 0 -> routes.firstOrNull { it.type == "R" }
            driverIdInt % 5 == 0 -> routes.filter { it.type == "C" }.getOrNull(1) // Get the second C type route
            else -> routes.lastOrNull { it.type == "I" }
        }
    }
}