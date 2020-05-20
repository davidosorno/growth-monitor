package com.dog.childgrowthmonitor.data.visit

import androidx.room.Embedded
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.child.Child

data class ChildPreviousVisits (
    @Embedded
    val child: Child,

    @Relation(
        parentColumn = "idChild",
        entityColumn = "child_id",
        entity = Visit::class
    )
    val visits: List<Visit>
)