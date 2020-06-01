package com.dog.childgrowthmonitor.data.address

import android.content.Context
import com.dog.childgrowthmonitor.data.GrowthMonitorDataBase
import com.dog.childgrowthmonitor.data.address.location.city.AddressesOfCity
import com.dog.childgrowthmonitor.data.address.location.city.City
import com.dog.childgrowthmonitor.data.address.location.city.CityDao
import com.dog.childgrowthmonitor.data.address.location.country.Country
import com.dog.childgrowthmonitor.data.address.location.country.CountryDao
import com.dog.childgrowthmonitor.data.address.location.country.StatesOfCountry
import com.dog.childgrowthmonitor.data.address.location.state.CitiesOfState
import com.dog.childgrowthmonitor.data.address.location.state.State
import com.dog.childgrowthmonitor.data.address.location.state.StateDao
import com.dog.childgrowthmonitor.data.insertOrUpdate


class LocationRepository(
    private val dataBase: GrowthMonitorDataBase?
){
    companion object {
        @Volatile
        private var instance: LocationRepository? = null

        fun getInstance(context: Context): LocationRepository? {
            return instance ?: synchronized(LocationRepository::class.java) {
                if (instance == null) {
                    val database = GrowthMonitorDataBase.getInstance(context)
                    instance = LocationRepository(database)
                }
                return instance
            }
        }
    }

//      ********** Address **********
    fun getAddresses(): List<AddressesOfCity>{
        val addressDao: AddressDao = dataBase!!.addressDao()
        return addressDao.getAddresses()
    }
    fun insert(address: Address){
        val addressDao: AddressDao = dataBase!!.addressDao()
        addressDao.insert(address)
    }


    //    ********** City **********
    fun getCities(): List<CitiesOfState>{
        val cityDao: CityDao = dataBase!!.cityDao()
        return cityDao.getCitiesOfState()
    }

    fun insert(city: City){
        val cityDao: CityDao = dataBase!!.cityDao()
        cityDao.insert(city)
    }


    //    ********** State **********
    fun getStates(): List<StatesOfCountry>{
        val stateDao: StateDao = dataBase!!.stateDao()
        return stateDao.getStatesOfCountry()
    }

    fun insert(state: State){
        val stateDao: StateDao = dataBase!!.stateDao()
        stateDao.insert(state)
    }


    //    ********** Country **********
    fun getCountries(): List<Country>{
        val countryDao: CountryDao = dataBase!!.countryDao()
        return countryDao.getCountries()
    }

    fun insert(country: Country){
        val countryDao: CountryDao = dataBase!!.countryDao()
        countryDao.insert(country)
    }
}