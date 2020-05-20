package com.dog.childgrowthmonitor.data.address.location.country

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dog.childgrowthmonitor.data.BasicDao

@Dao
interface CountryDao: BasicDao<Country> {

    @Transaction
    @Query("SELECT * FROM country")
    fun getCountries(): List<Country>
}