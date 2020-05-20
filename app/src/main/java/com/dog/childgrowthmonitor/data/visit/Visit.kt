package com.dog.childgrowthmonitor.data.visit

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dog.childgrowthmonitor.data.child.Child
import java.util.*

@Entity(
    indices = [Index("idVisit", "child_id")],
    foreignKeys = [
        ForeignKey(
            entity = Child::class,
            parentColumns = ["idChild"],
            childColumns = ["child_id"]
        )
    ]
)
data class Visit (
    @PrimaryKey(autoGenerate = true)
    val idVisit: Long = 0L,

    @NonNull
    val dateVisit: Date = Date(),
    var observerId: Int = 0, // TODO I'm not sure about this field yet

    @NonNull
    var height: Float = 0F,

    @NonNull
    var weight: Float = 0F,

    var measured: Int = 0, // 0 Standing ... 1 Recumbent
    var oedema: Boolean = false,

    val child_id: Long
)