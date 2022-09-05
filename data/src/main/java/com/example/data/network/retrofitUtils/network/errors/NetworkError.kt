package com.example.data.network.retrofitUtils.network.errors

import java.io.IOException

/**
 * Network error
 */
data class NetworkError(val error: IOException) : Exception()