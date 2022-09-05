package com.example.data.db.utils

import android.content.Context

class PathProvider(
    private val context: Context
) {
    fun getAbsolutePath(): String =
        context.filesDir.absolutePath
}