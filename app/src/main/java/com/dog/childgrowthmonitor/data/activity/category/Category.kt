package com.dog.childgrowthmonitor.data.activity.category

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idCategory")])
class Category (
    @PrimaryKey(autoGenerate = true)
    val idCategory: Long = 0L,

    @NonNull
    val nameActivity: String = ""
)