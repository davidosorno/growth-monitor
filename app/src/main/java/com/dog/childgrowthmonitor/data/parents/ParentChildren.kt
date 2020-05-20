package com.dog.childgrowthmonitor.data.parents

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.child.Child

data class ParentChildren(
    @Embedded
    val parent: Parent,

    @Relation(
        parentColumn = "idParent",
        entityColumn = "idChild",
        associateBy = Junction(ParentChildrenCrossRef::class)
    )
    val children: List<Child>
)