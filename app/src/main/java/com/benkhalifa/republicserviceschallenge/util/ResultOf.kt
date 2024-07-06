package com.benkhalifa.republicserviceschallenge.util

sealed class ResultOf {
    data class Success(val message: String) : ResultOf()
    data class Error(val message: String) : ResultOf()
    data object Loading : ResultOf()
}
