package com.benkhalifa.republicserviceschallenge.domain

data class Driver(
    var id: String,
    var name: String
)

data class Route(
    val id: Int,
    val type: String,
    val name: String
)