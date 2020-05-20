package com.dog.childgrowthmonitor.data.address.location.state

import androidx.room.Embedded
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.address.location.city.City

data class CitiesOfState (
    @Embedded
    val state: State,

    @Relation(
        parentColumn = "idState",
        entityColumn = "state_id"
//        entity = City::class
    )
    val addresses: List<City>
)