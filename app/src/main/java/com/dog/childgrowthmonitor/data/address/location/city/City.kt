package com.dog.childgrowthmonitor.data.address.location.city

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idCity")])
data class City(
    @PrimaryKey(autoGenerate = true)
    val idCity: Long = 0L,

    @NonNull
    val nameCity: String = "",

//    Foreign Key
    val state_id: Long = 0L
)