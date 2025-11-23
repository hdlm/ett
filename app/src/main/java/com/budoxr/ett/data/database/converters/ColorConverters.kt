package com.budoxr.ett.data.database.converters

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import androidx.room.TypeConverter

class ColorConverters {
    @TypeConverter
    fun fromColor(color: Color): Long? {
        return color.toColorLong()
    }

    @TypeConverter
    fun toColor(colorValue: Long?): Color? {
        return colorValue?.let { Color(it) }
    }
}