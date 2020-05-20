package com.dog.childgrowthmonitor.ui.add.child

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.ChildRepository
import java.lang.IllegalArgumentException

class AddChildViewModelFactory(
    val context: Context,
    val resources: Resources
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddChildViewModel::class.java)){
            return AddChildViewModel(
                ChildRepository.getInstance(context)!!,
                resources
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}