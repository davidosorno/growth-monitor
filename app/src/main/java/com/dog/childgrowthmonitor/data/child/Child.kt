package com.dog.childgrowthmonitor.data.child

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dog.childgrowthmonitor.data.interfaces.Person
import kotlinx.android.parcel.Parcelize

@Entity(indices = [
        Index("idChild"),
        Index("firstName", "lastName", unique = true)
    ]
)
@Parcelize
data class Child(
    @PrimaryKey(autoGenerate = true)
    var idChild: Long = 0L
):Parcelable, Person()