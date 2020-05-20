package com.dog.childgrowthmonitor.ui.add.user

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.user.UserRepository
import java.lang.IllegalArgumentException

class AddUserFactory(
    val context: Context,
    val resources: Resources
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddUserViewModel::class.java)){
            return AddUserViewModel(
                UserRepository.getInstance(context)!!,
                resources
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}