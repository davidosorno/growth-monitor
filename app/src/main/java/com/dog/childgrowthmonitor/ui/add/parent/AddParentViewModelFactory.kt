package com.dog.childgrowthmonitor.ui.add.parent

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.parents.ParentRepository
import java.lang.IllegalArgumentException

class AddParentViewModelFactory(
    private val context: Context,
    private val child: Child,
    private val resources: Resources,
    private val typeParent: Int
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddParentViewModel::class.java)) {
            return AddParentViewModel(
                ParentRepository.getInstance(context)!!,
                child,
                resources,
                typeParent
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}