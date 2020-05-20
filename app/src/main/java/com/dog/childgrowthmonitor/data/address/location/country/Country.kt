package com.dog.childgrowthmonitor.data.address.location.country

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idCountry")])
data class Country(
    @PrimaryKey(autoGenerate = true)
    val idCountry: Long = 0,

    @NonNull
    val nameCountry: String = ""
)