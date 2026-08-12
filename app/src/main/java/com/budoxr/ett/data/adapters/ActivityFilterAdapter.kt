/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class ActivityFilterAdapter(private val moshi: Moshi) {
    val adapter: JsonAdapter<LongArray> =
        moshi.adapter(LongArray::class.java)
}
