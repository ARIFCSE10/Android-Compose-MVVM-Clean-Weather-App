package com.badsha.weatherappcompose.feature.util

import android.content.Context

object DrawableUtil {
    fun getDrawableId(drawableName: String, context: Context): Int {
        return context.resources.getIdentifier(
            drawableName, "drawable",
            context.packageName
        )
    }
}