package com.dog.childgrowthmonitor.data.interfaces

import java.util.*

abstract class Person(
    var firstName: String = "",
    var lastName: String = "",
    var gender: Int = 1, //1 Male    2 Female
    var birth: Date = Date(),
    var updateAt: Date  = Date()
){
    val fullName: String
        get() = "$firstName $lastName"

    val createdAt: Date
        get() = Date()
}