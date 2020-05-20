package com.dog.childgrowthmonitor.data.user

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user",
    indices = [
        Index("idUser"),
        Index("firstName", "lastName", unique = true)
    ]
)
class User(
    @PrimaryKey(autoGenerate = true)
    val idUser: Long = 0L,

    @NonNull
    var firstName: String = "",

    @NonNull
    var lastName: String = "",

    @NonNull
    var gender: Int = 0, //1 Male --- 2 Female

    @NonNull
    var birth: Date = Date(),

    @NonNull
    var parent: Boolean = false
)