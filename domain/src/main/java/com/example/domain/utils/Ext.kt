package com.example.domain.utils

import android.content.Context
import android.provider.Settings

fun isAirplaneMode(context: Context): Boolean {
    return Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
}