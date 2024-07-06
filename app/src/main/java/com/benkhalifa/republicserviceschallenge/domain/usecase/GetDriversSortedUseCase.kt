package com.benkhalifa.republicserviceschallenge.domain.usecase

import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.repository.TransportRepository
import javax.inject.Inject

class GetDriversSortedUseCase @Inject constructor(private val transportRepository: TransportRepository) {

    suspend fun getDriversSortedByLastName(): List<Driver> {
        val routes: List<Driver> = transportRepository.getDriversData()
        return routes.sortedBy {
            it.name.substringAfterLast(" ")
        }
    }
}