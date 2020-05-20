package com.dog.childgrowthmonitor.data.parents

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dog.childgrowthmonitor.data.interfaces.Person
import kotlinx.android.parcel.Parcelize

@Entity(indices = [
        Index("idParent"),
        Index("firstName", "lastName", unique = true)
    ]
)
@Parcelize
data class Parent(
    @PrimaryKey(autoGenerate = true)
    var idParent: Long = 0L,
    @NonNull
    var height: Int = 0, //cms
    @NonNull
    var weight: Float = 0F //kg

): Parcelable, Person()