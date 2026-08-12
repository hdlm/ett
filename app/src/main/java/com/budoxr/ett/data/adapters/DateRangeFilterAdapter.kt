/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

@JsonClass(generateAdapter = true)
data class DateRangeJson(val start: String, val end: String)

class DateRangeFilterAdapter(private val moshi: Moshi) {

    private val jsonAdapter: JsonAdapter<DateRangeJson> =
        moshi.adapter(DateRangeJson::class.java)

    fun toJson(pair: Pair<String, String>): String {
        return jsonAdapter.toJson(DateRangeJson(pair.first, pair.second))
    }

    fun fromJson(json: String): Pair<String, String>? {
        return jsonAdapter.fromJson(json)?.let { it.start to it.end }
    }
}
