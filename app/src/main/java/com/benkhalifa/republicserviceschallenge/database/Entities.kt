package com.benkhalifa.republicserviceschallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benkhalifa.republicserviceschallenge.domain.Driver
import com.benkhalifa.republicserviceschallenge.domain.Route

@Entity
data class DatabaseDriver(
    @PrimaryKey
    val id: String,
    val name: String
)

@Entity
data class DatabaseRoute(
    @PrimaryKey
    val id: Int,
    val type: String,
    val name: String
)

fun List<DatabaseDriver>.asDriverDomainModel(): List<Driver> {
    return map {
        Driver(
            id = it.id,
            name = it.name
        )
    }
}

fun List<DatabaseRoute>.asRouteDomainModel(): List<Route> {
    return map {
        Route(
            id = it.id,
            type = it.type,
            name = it.name
        )
    }
}