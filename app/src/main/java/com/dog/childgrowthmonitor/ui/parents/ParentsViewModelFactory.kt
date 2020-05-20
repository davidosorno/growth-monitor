package com.dog.childgrowthmonitor.ui.parents

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildRepository
import com.dog.childgrowthmonitor.data.parents.ParentRepository
import java.lang.IllegalArgumentException

class ParentsViewModelFactory(
    private val context: Context,
    private val child: Child
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParentsViewModel::class.java)) {
            return ParentsViewModel(
                ChildRepository.getInstance(context)!!,
                ParentRepository.getInstance(context)!!,
                child,
                context.resources
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}