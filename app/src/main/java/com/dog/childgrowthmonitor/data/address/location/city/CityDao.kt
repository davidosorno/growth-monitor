package com.dog.childgrowthmonitor.data.address.location.city

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dog.childgrowthmonitor.data.BasicDao
import com.dog.childgrowthmonitor.data.address.location.state.CitiesOfState

@Dao
interface CityDao: BasicDao<City> {

    @Transaction
    @Query("SELECT * FROM state")
    fun getCitiesOfState(): List<CitiesOfState>
}