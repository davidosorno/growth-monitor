package com.dog.childgrowthmonitor.data.child

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentChildrenCrossRef

data class ChildParents (
    @Embedded
    val child: Child,

    @Relation(
        parentColumn = "idChild",
        entityColumn = "idParent",
        associateBy = Junction(ParentChildrenCrossRef::class)
    )
    val parents: List<Parent>
)