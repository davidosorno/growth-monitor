package com.dog.childgrowthmonitor.data.address.location.state

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idState")])
data class State (
    @NonNull
    @PrimaryKey
    val idState: Long = 0L,

    @NonNull
    val nameState: String = "",

//    Foreign Key
    val country_id: Long = 0
)