package com.dog.childgrowthmonitor.data.activity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idActivity")])
class Activity (
    @PrimaryKey(autoGenerate = true)
    val idActivity: Long = 0L,

    @NonNull
    val nameActivity: String = "",

    @NonNull
    val category_id: Long = 0L
)