package com.dog.childgrowthmonitor.data.address

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("idAddress")])
data class Address(
    @PrimaryKey(autoGenerate = true)
    val idAddress: Long = 0L,

    val zipCode: Int = 0,

    val phone: String = "",

    val email: String = "",

   //Foreign key
    val city_id: Long = 0L
)