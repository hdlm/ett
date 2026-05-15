/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
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