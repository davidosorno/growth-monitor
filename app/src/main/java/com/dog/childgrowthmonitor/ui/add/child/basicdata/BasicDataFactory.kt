package com.dog.childgrowthmonitor.ui.add.child.basicdata

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildRepository
import java.lang.IllegalArgumentException

class BasicDataFactory(
    val context: Context,
    val child: Child
) : ViewModelProvider.Factory{

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BasicDataViewModel::class.java)){
            return BasicDataViewModel(
                ChildRepository.getInstance(context)!!,
                child,
                context.resources
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}