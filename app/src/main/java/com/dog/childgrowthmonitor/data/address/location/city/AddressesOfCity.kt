package com.dog.childgrowthmonitor.data.address.location.city

import androidx.room.Embedded
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.address.Address

data class AddressesOfCity(
    @Embedded
    val city: City,

    @Relation(
        parentColumn = "idCity",
        entityColumn = "city_id"
//        entity = Address::class
    )
    val addresses: List<Address>
)