package com.example.feature.base.utils

typealias ClickListener = () -> Unit
typealias Typed1Listener<T> = (T) -> Unit
typealias Typed2Listener<T, M> = (T, M) -> Unit
typealias Typed3Listener<T, M> = (T, M) -> Unit


typealias MaterialTheme2 = androidx.compose.material.MaterialTheme