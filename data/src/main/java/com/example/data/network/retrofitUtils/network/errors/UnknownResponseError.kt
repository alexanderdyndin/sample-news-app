package com.example.data.network.retrofitUtils.network.errors

/**
 * For example, json parsing error
 */
data class UnknownResponseError(val error: Throwable?) : Exception()