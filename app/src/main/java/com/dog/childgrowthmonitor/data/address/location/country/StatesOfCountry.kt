package com.dog.childgrowthmonitor.data.address.location.country

import androidx.room.Embedded
import androidx.room.Relation
import com.dog.childgrowthmonitor.data.address.location.state.State

data class StatesOfCountry (
    @Embedded
    val country: Country,

    @Relation(
        parentColumn = "idCountry",
        entityColumn = "country_id"
//        entity = State::class
    )
    val addresses: List<State>
)