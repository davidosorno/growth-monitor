package com.dog.childgrowthmonitor.data.visit

import androidx.annotation.NonNull
import androidx.room.*
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

    @NonNull
    var height: Float = 0F,

    @NonNull
    var weight: Float = 0F,

    var measured: Int = 0, // 0 Standing ... 1 Recumbent
    var oedema: Boolean = false,

    @ColumnInfo(index = true)
    val child_id: Long
)