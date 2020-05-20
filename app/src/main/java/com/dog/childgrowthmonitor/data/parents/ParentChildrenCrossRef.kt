package com.dog.childgrowthmonitor.data.parents

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["idChild", "idParent"],
    indices = [Index("idChild", "idParent")]
)
data class ParentChildrenCrossRef(
    val idChild: Long,
    val idParent: Long
)