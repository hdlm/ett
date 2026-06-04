package com.budoxr.ett.data.mapper

import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.presentation.domain.ActivityModel

fun ActivityEntity.toModel() = ActivityModel(
    activityId = activityId,
    name = name,
    color = color,
)


fun ActivityModel.toEntity() = ActivityEntity(
    activityId = activityId,
    name = name,
    color = color,
)