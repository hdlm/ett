/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.database.converters

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter

class ColorConverters {
    @TypeConverter
    fun fromColor(color: Color?): Int? {
        return color?.toArgb()
    }

    @TypeConverter
    fun toColor(colorValue: Int?): Color? {
        return colorValue?.let { Color(it) }
    }
}
