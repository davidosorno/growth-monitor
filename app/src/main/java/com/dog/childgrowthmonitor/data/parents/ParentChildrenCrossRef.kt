package com.dog.childgrowthmonitor.data.parents

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["idChild", "idParent"],
    indices = [Index("idChild", "idParent")]
)
data class ParentChildrenCrossRef(
    @ColumnInfo(index = true)
    val idChild: Long,

    @ColumnInfo(index = true)
    val idParent: Long
)