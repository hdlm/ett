/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.adapters

import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class TimerTrackingQueryAdapter(private val moshi: Moshi) {
    val adapter: JsonAdapter<List<TimerTrackingQuery>> =
        moshi.adapter(
            Types.newParameterizedType(
                List::class.java,
                TimerTrackingQuery::class.java
            )
        )
}