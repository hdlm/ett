package com.budoxr.ett.commons

import android.graphics.Bitmap
import androidx.compose.runtime.Composable

typealias onDismissType = () -> Unit
typealias onIntType = (Int) -> Unit
typealias onLongType = (Long) -> Unit
typealias onDoubleType = (Double) -> Unit
typealias onBooleanType = (Boolean) -> Unit
typealias onBooleanReturn = () -> Boolean
typealias onStringType = (String) -> Unit
typealias onDismissComposableType = @Composable () -> Unit
typealias onDismissTypeSuspend = suspend () -> Unit
typealias onBitmapType = (String) -> Bitmap?