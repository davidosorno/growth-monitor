package com.dog.childgrowthmonitor.data.address

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.dog.childgrowthmonitor.data.BasicDao
import com.dog.childgrowthmonitor.data.address.location.city.AddressesOfCity

@Dao
interface AddressDao: BasicDao<Address>{

    @Transaction
    @Query("SELECT * FROM city")
    fun getAddresses(): List<AddressesOfCity>

//    @Transaction
//    @Query("SELECT * FROM cityaddresses")
//    fun getCities(): List<StateCities>
//
//    @Transaction
//    @Query("SELECT * FROM cityaddresses")
//    fun getStates(): List<CountryStates>

}

//TODO MUY INTERESANTE ACERCA DE CIERTAS FUNCIONES BASICAS KOTLIN
//https://blog.autsoft.hu/top-10-kotlin-stack-overflow-questions/
//Here's a simple example of using the collection functions to get the names of the first five people in a list whose age is at least 21:
//
//val people: List<Person> = getPeople()
//val allowedEntrance = people
//		.filter { it.age >= 21 }
//		.map { it.name }
//		.take(5)


//for (i in args.indices) {
//	println(args[i])
//}
//But you can also iterate the collection directly instead of a range of indexes, with this syntax:
//
//for (arg in args) {
//	println(arg)
//}
//Or you can go a bit functional and use the forEach function, passing a lambda to it that will do the work on each element:
//
//args.forEach { arg ->
//	println(arg)
//}