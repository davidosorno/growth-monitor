package com.dog.childgrowthmonitor.ui.visit.weightandheight

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.visit.VisitRepository
import java.lang.IllegalArgumentException

class WeightAndHeightFactory(
    val context: Context,
    val child: Child
):ViewModelProvider.Factory {
    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeightAndHeightViewModel::class.java)){
            return WeightAndHeightViewModel(
                VisitRepository.getInstance(context)!!,
                context.resources,
                child
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}